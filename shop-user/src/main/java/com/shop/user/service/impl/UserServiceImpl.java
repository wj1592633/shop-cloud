package com.shop.user.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.api.feign.order.service.OrderService;
import com.shop.common.constant.Constant;
import com.shop.common.dto.ResponseResult;
import com.shop.common.enumation.ExceptionEnum;
import com.shop.common.enumation.OrderStateEnum;
import com.shop.common.exception.CustomException;
import com.shop.entity.PayRepeat;
import com.shop.entity.User;
import com.shop.user.config.security.UserDetailsImpl;
import com.shop.user.mapper.PayRepeatMapper;
import com.shop.user.mapper.UserMapper;
import com.shop.user.properties.SecurityProperties;
import com.shop.user.service.IUserService;
import com.shop.user.util.SecurityKit;
import com.shop.user.vo.UserInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

/**
 * <p>
 * 用户表，简单处理，不分卖主和买主 服务实现类
 * </p>
 *
 * @author wj
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    PayRepeatMapper payRepeatMapper;
    @Autowired
    RocketMQTemplate rocketMQTemplate;
    @Autowired
    SecurityProperties properties;
    @Autowired
    OrderService orderService;
    @Autowired
    TransactionTemplate transactionTemplate;

    @Override
    public void sendMsg() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","zhangsan");
        String s = jsonObject.toString();
        Message<String> msg = MessageBuilder.withPayload(s).build();
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction("producer_order_txmsg_shop", "topic_txmsg",
                msg, null
        );
    }

    @Override
    public UserInfo login(User user) {
        try {
            UserInfo userInfo = applyToken(user.getUsername(), user.getPassword());
            return userInfo;
        }catch (Exception e){
            String message = e.getMessage();
            //密码错误，有可能是自己账号但密码错。也可能是误输入别人账号，密码对不上，提示账号错误
            if(message.indexOf("Bad credentials") != -1){
                throw new CustomException(ExceptionEnum.PWD_OR_ACC_ERROR);
            }else if(message.indexOf("server authentication") != -1){
                //查不到账号
                throw new CustomException(ExceptionEnum.ERROR_ACCOUNT);
            }
            throw e;
        }
    }

    @Override
    public ResponseResult prePayForOrder(String orderId, String userId) {
        if(StringUtils.isBlank(orderId))
            throw new CustomException(ExceptionEnum.NULL_ARGS);
        PayRepeat payRepeat = payRepeatMapper.selectById(orderId);
        //订单已经支付过
        if (null != payRepeat){
            throw new CustomException(ExceptionEnum.HAD_PAY);
        }
        //在次判断是否登录
        String loginId = SecurityKit.getUserId();
        if (StringUtils.isBlank(userId))
            throw new CustomException(ExceptionEnum.NEED_LOGIN);

        //应该在这判断该订单是不是自己的订单.其实应该去订单模块查询得到userid再作比较才对
        if (! loginId.equals(userId)){
            throw new CustomException(ExceptionEnum.NO_WORK);
        }

        ResponseResult orderByOrderId = orderService.getOrderByOrderId(orderId);

        if (orderByOrderId != null && orderByOrderId.getState() == Constant.RESPONSE_CODE_SUCCESS) {
            LinkedHashMap order = (LinkedHashMap) orderByOrderId.getData();
            validOrderById(order);
        }
        //发送事务消息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId",orderId);
        String s = jsonObject.toString();
        Message<String> msg = MessageBuilder.withPayload(s).build();
        //事务消息。
        /**
         *参数1：生产组，和配置文件的生成组不能同名
         */
        TransactionSendResult result = rocketMQTemplate.sendMessageInTransaction(Constant.TXMSG_PRODUCER_GROUP_USER, Constant.TXMSG_TOPIC_ORDER_STATE,
                msg, null
        );
        //发送成功
        if(SendStatus.SEND_OK.equals(result.getSendStatus())){
           return ResponseResult.ok("支付成功");
        }
        return ResponseResult.fail("支付失败");
    }
    private void validOrderById(Map order) {
        if (order == null)
            throw new CustomException(ExceptionEnum.NO_WORK);

        int state = (int)order.get("state");
        //订单状态已经支付
        if (state == OrderStateEnum.HAD_PAY.getStateNum().intValue()){
            throw new CustomException(ExceptionEnum.HAD_PAY);
        }else if (state == OrderStateEnum.CANCEL_TIME_OUT.getStateNum().intValue()){
            //超时订单
            throw new CustomException(ExceptionEnum.TIME_OUT_ORDER);
        }else if (state == OrderStateEnum.CANCEL_PAY_USER.getStateNum().intValue()){
            //用户取消的订单
            throw new CustomException(ExceptionEnum.ILLEGAL_ORDER);
        }
    }
    @Override
    public ResponseResult payForOrder(String orderId) {
        if(StringUtils.isBlank(orderId))
            throw new CustomException(ExceptionEnum.NULL_ARGS);
        PayRepeat payRepeat = payRepeatMapper.selectById(orderId);
        //订单已经支付过
        if (null != payRepeat){
            throw new CustomException(ExceptionEnum.HAD_PAY);
        }
        //查询订单信息
        ResponseResult orderByOrderId = orderService.getOrderByOrderId(orderId);

        if (orderByOrderId != null && orderByOrderId.getState() == Constant.RESPONSE_CODE_SUCCESS) {
            LinkedHashMap order =(LinkedHashMap) orderByOrderId.getData();
            validOrderById(order);
            //下单的用户id
            String fromId = (String)order.get("userId");
            //不可以 不是自己支付自己的订单
            if (! SecurityKit.isCurrentUser(fromId)){
                throw new CustomException(ExceptionEnum.NEED_LOGIN);
            }
            //要向支付的商家用户id
            String toId = orderId.substring(orderId.lastIndexOf("-") + 1);
            //要支付的金额
            BigDecimal price = BigDecimal.valueOf((double)order.get("payPrice"));
            User toUser = userMapper.selectById(toId);
            User fromUser = userMapper.selectById(fromId);
            BigDecimal toAmount = toUser.getAmount();
            BigDecimal fromAmount = fromUser.getAmount();
            BigDecimal add = toAmount.add(price);
            BigDecimal subtract = fromAmount.subtract(price);
            //下单用户金额不足无法购买
            if (fromAmount.compareTo(price) == -1)
                throw new CustomException(ExceptionEnum.NO_MONEY);
            transactionTemplate.execute((status) -> {
                //模拟转账。version用作乐观锁
                int i = userMapper.updateAmount(fromId, subtract, fromUser.getVersion());
                int r = userMapper.updateAmount(toId, add, toUser.getVersion());
                PayRepeat payRepeat1 = new PayRepeat();
                payRepeat1.setOrderId(orderId);
                //防止重复转账
                int i2 = payRepeatMapper.insert(payRepeat1);
                //有失败则回滚
                if (i != 1 || r != 1 || i2 != 1){
                    throw new CustomException(ExceptionEnum.BAD_NET);
                }
                return null;
            });

        }else {
            //查不到数据，抛出异常。或者feign调用失败时orderByOrderId.getState()为400之类
            throw new CustomException(ExceptionEnum.BAD_NET);
        }
        return null;
    }

    @Override
    public BigDecimal getAmount() {
        String userId = SecurityKit.getUserId();
        if (StringUtils.isBlank(userId)){
            throw new CustomException(ExceptionEnum.NEED_LOGIN);
        }
        BigDecimal amount = userMapper.getAmountByUserId(userId);
        if (amount.compareTo(BigDecimal.ZERO) < 0){
            throw new CustomException(ExceptionEnum.BAD_NET);
        }
        return userMapper.getAmountByUserId(userId);
    }

    @Autowired
    LoadBalancerClient balancerClient;
    /**
     *密码方式申请令牌
     */
    private UserInfo applyToken(String username, String password){
        //动态获取认证服务的地址端口
        //ServiceInstance serviceInstance = balancerClient.choose("shop-user");
        //URI uri = serviceInstance.getUri();
        //String applyURL = uri + "/oauth/token";
        //restTemplate用于发送远程请求
        RestTemplate restTemplate = new RestTemplate();
        //SpringSecurity认证错误时，错误码为401或400，restTemplate发现错误就不执行，拿不回结果，
        //所以得设置错误处理setErrorHandler
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if(response.getRawStatusCode() != 400 && response.getRawStatusCode()!=401){
                    super.handleError(response);
                }
            }
        });

        //头部带的参数
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        //base64编码client_id和client_secret，oauth2必须带上这2个，认证服务器才能知道时哪个应用在请求
        String basic = getBasic(properties.getClientId(), properties.getClientSecret());
        header.add("Authorization",basic);

        //body带的参数
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        //参数固定这么写
        body.add("grant_type","password");
        body.add("username",username);
        body.add("password",password);
        //scope,可以根据需求变动
        body.add("scope",properties.getScopes()[0]);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, header);
        //发送远程请求
        ResponseEntity<Map> exchange = restTemplate.exchange(properties.getGetTokenUrl(), HttpMethod.POST, entity, Map.class);
        Map jwtMap = exchange.getBody();
        //jwtMap认证错误时，access_token等都为空，但会有error_description
        if(jwtMap == null ||
                jwtMap.get("access_token") == null ||
                jwtMap.get("refresh_token") == null ||
                jwtMap.get("jti") == null) {
            //解析spring security返回的错误信息
            if (jwtMap != null && jwtMap.get("error_description") != null) {
                String error_description = (String) jwtMap.get("error_description");
                if (StringUtils.isNoneBlank(error_description)) {
                    if (error_description.indexOf("disabled") != -1) {
                        //用户状态不为1，表示状态异常，不可用
                        throw new CustomException(ExceptionEnum.USER_INVALID);
                    }else if(error_description.indexOf("locked") != -1){
                        //用户被锁定
                        throw new CustomException(ExceptionEnum.USER_INVALID);
                    }else if(error_description.indexOf("Bad credentials") != -1){
                        //密码错误
                        throw new CustomException(ExceptionEnum.PWD_NOT_RIGHT);
                    }
                }

            }
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);

        //根据账号查询用户
        User user = getOne(wrapper);
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname(user.getNickname());
        userInfo.setAmount(user.getAmount());
        userInfo.setState(user.getState());
        userInfo.setUserId(user.getUserId());
        userInfo.setUsername(username);
        if (null != userInfo) {
            userInfo.setSalt(null);
            userInfo.setPassword(null);
            userInfo.setVersion(null);
            userInfo.setJwtToken((String) jwtMap.get("access_token"));
            userInfo.setRefreshToken((String) jwtMap.get("refresh_token"));
            userInfo.setTokenKey((String) jwtMap.get("jti"));
        }
        return userInfo;
    }

    /**
     * 访问/oauth/token时，头部要带上base64编码后的clientId和clientSecret
     * @param clientId
     * @param clientSecret
     * @return
     */
    private String getBasic(String clientId,String clientSecret){
        String value = clientId+":"+clientSecret;
        byte[] encode = Base64Utils.encode(value.getBytes());
        return "Basic "+new String(encode);
    }

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 测试手动生成jwt，实测可以生成
     * @return
     */
    @Override
    public Object create() {
        String permissions = "user/list,goods/list,order/add";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(permissions);
        UserDetailsImpl userDetails = new UserDetailsImpl("aaa","", grantedAuthorities);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,null,grantedAuthorities);
        //token.setAuthenticated(true);
        LinkedHashMap<Object, Object> details = new LinkedHashMap<>();
        details.put("client_id","test");
        details.put("client_secret","test");
        details.put("grant_type","password");
        details.put("username","aaa");
        token.setDetails(details);


        Map<String, String> requestParameters = new LinkedHashMap<>();
        requestParameters.put("client_id","test");
        requestParameters.put("grant_type","password");
        requestParameters.put("username","aaa");
        Set<String> scope = new LinkedHashSet<>();
        scope.add("all");
        Set<String> resourceIds = new LinkedHashSet<>();
        resourceIds.add("user_service");

        Set<String> responseTypes = new HashSet<>();
        Map<String, Serializable> extensionProperties = new HashMap<>();
        OAuth2Request sortRequest = new OAuth2Request(requestParameters,"test",grantedAuthorities,true,scope,resourceIds,null,responseTypes,extensionProperties);
        OAuth2Authentication auth2Authentication = new OAuth2Authentication(sortRequest,token);
        DefaultTokenServices tokenService = applicationContext.getBean(DefaultTokenServices.class);
        OAuth2AccessToken accessToken = tokenService.createAccessToken(auth2Authentication);
        return accessToken;
    }

    @Override
    public Object getOrder() {
        ResponseResult orderByOrderId = orderService.getOrderByOrderId("order-323-20200413015436-915-u1");
        return orderByOrderId;
    }
}
