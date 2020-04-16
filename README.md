*shop-back和shop-portal是前后端分离的，前端写的比较简陋。shop-portal地址 https://github.com/wj1592633/shop-portal
*shop-back是后台服务，里面有些多余的模块，主要是开始考虑用mongodb或者其他东西，后来不用了。
  主要使用到springcloud系列相关部件、redis、rocketmq、mysql、mybatis-plus
*所有模块：
  - shop-parent：管理maven依赖版本
  - shop-eureka：微服务注册中心
  - shop-gateway-cloud：微服务门户(shop-gateway不使用了，其跨域问题还没处理)
  - shop-common：添加一些公共maven依赖，及公共工具类
  - shop-common-web：添加一些公共maven依赖
  - shop-goods：商品模块，主要用于查询商品信息
  - shop-goods-service-api：商品模块的feign接口
  - shop-order：订单模块
  - shop-order-service-api：订单模块的feign接口
  - shop-user：用户模块。资源认证
  - shop-user-service-api：用户模块的feign接口
*shop-back功能比较简单，登录后，可以查看商品、订单，商品有不同规格(sku)，购买一次买一件。
  用户有amount字段，用于模拟付款。
  控制超卖问题：主要用redis，由于redis是用单线程接收请求，处理完后再返回结果，只要使用lua脚本把多步操作存一起便可以。把商品信息存在redis，
                 每次买都通过lua脚本到redis判断商品数量，数量大于0则redis中的商品数量减1，并完成订单构建。数量小于0则返回-1，不进行处理。
                 脚本位置：shop-common模块com.shop.common.script.RedisScript
  模拟付款：该功能要进行幂等处理。(该功能要对用户表和订单表进行事务一致控制，使用强一致的两段提交比较好点，比如阿里的seata，但是由于电脑太卡，
            使用了MQ方式。)用户模块进行模拟，先到PayRepeat查询是否有记录，有记录则说明该订单已经支付过了，不能再次支付，再到order模块查询订单信息，
            对该订单进行状态判断，付款过了就不能再次付款，付款之后，会对PayRepeat插入记录，PayRepeat只有id字段并且是主键，一旦重复插入则出错回滚。
            只有以上全都通过了，rocketMq事务消息才会真正发送到订单模块，修改订单状态。
*shop-goods模块：主要用于查询商品和商品的sku，以及从数据库加载商品信息到redis，使用了redis作缓存。该模块可以不用登录直接访问
*shop-order模块：主要用于查询、添加、修改订单。在下单的同时会发送一条延迟消息到mq，该订单在1min之后不支付会被取消掉
*shop-user模块：该模块和认证模块整合一起，使用了springOauth2结合jwt进行认证及授权。