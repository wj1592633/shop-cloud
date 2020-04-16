package com.shop.order.controller;


import com.shop.common.dto.ResponseResult;
import com.shop.common.enumation.OrderStateEnum;
import com.shop.common.util.ToolUtils;
import com.shop.entity.Order;
import com.shop.entity.util.PageUtil;
import com.shop.order.service.IOrderService;
import com.shop.order.vo.OderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wj
 */
@RestController
public class OrderController {
    @Autowired
    IOrderService orderService;

    @PostMapping("/order/add")
    public ResponseResult addOrder(OderData data){
       return orderService.addOrder(data);
    }

    /**
     * 分页查询自己所有的订单
     * @param pageUtil
     * @return
     */
    @GetMapping("/order/records")
    public ResponseResult getOrderByUserId(PageUtil pageUtil){
        PageUtil result = orderService.getOrderByUserId(pageUtil);
        return ResponseResult.ok(result);
    }

    @RequestMapping(value = "/order/detail", method = RequestMethod.GET)
    public ResponseResult getOrderByOrderId(@RequestParam("orderId") String orderId){
        Order order = orderService.getById(orderId);
        Map<String, Object> map = ToolUtils.bean2Map(order);
        map.put("stateStr", OrderStateEnum.getStateStrByStateNum(order.getState()));
        return ResponseResult.ok(map);
    }

    /*@PostMapping("/order/finish")
    public ResponseResult finishOrder(String orderId){
        int i = orderService.changeOrderStateById(orderId, OrderStateEnum.NEED_PAY.getStateNum(), OrderStateEnum.HAD_PAY.getStateNum());
        if (i == 1)
            return ResponseResult.ok();
        return ResponseResult.fail();
    }*/

    /**
     * DeferredResult和Callable 作用：可以释放服务器的主线程，从而提高服务器的吞吐量
     * DeferredResult使用场景: 订单付款时，请求发送到用户模块，用户模块扣钱，订单模块改变订单状态并
     * 向mq发送消息，模糊模块监听，一旦监听到订单状态改变的消息，回应用户请求
     * 用法:用一个全局的Map<String,DeferredResult>保存DeferredResult，等处理完业务再从Map中取出来设置值
     * @param orderId
     * @return
     * @throws InterruptedException
     */
    /*@GetMapping("/test/asy")
    public DeferredResult<ResponseResult> testAddOrder(String orderId) throws InterruptedException {
        String s = RandomStringUtils.randomNumeric(8);
        s = s + ";;;;->" + orderId;
        TestAsyHandle.queue.put(s);
        DeferredResult<ResponseResult> result = new DeferredResult<>();
        TestAsyHandle.map.put(s,result);
        return result;
    }
    @Autowired
    RocketMQTemplate rocketMQTemplate;
    @GetMapping("/test/asy2")
    public DeferredResult<ResponseResult> testAddOrder2(String orderId) throws InterruptedException {
        //普通消息
        Order order = Order.builder().orderId(orderId).payPrice(BigDecimal.valueOf(20)).skuId("sku2").build();
        rocketMQTemplate.convertAndSend("top_asy_test", order);
        DeferredResult<ResponseResult> result = new DeferredResult<>();
        TestAsyHandle.map.put(orderId,result);
        return result;
    }

    @GetMapping("/test/asy1")
    public Callable<ResponseResult> testAddOrder1(String orderId) throws InterruptedException {
        final String s = RandomStringUtils.randomNumeric(8);
        final String s1 = s + ";;;;->" + orderId;
        TestAsyHandle1.queue.put(s);
        Callable<ResponseResult> result = () ->{

            return ResponseResult.ok(s1);
        };
        return result;
    }*/
}
