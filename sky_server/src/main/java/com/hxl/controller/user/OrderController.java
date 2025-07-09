package com.hxl.controller.user;

import com.hxl.dto.HistoryOrdersDTO;
import com.hxl.dto.OrderPaymentDTO;
import com.hxl.dto.OrderSubmitDTO;
import com.hxl.entity.Orders;
import com.hxl.result.PageResult;
import com.hxl.result.Result;
import com.hxl.service.OrderService;
import com.hxl.vo.OrderPaymentVO;
import com.hxl.vo.OrderSubmitVO;
import com.hxl.vo.OrderVO;
import com.sun.xml.bind.v2.TODO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderSubmitController")
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "用户下单相关的接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //消息队列封装类
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 用户下单
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submitOrder(@RequestBody OrderSubmitDTO orderSubmitDTO) {
        log.info("用户下单: {}", orderSubmitDTO);

        //提交当前用户的订单
        OrderSubmitVO vo = orderService.submitOrder(orderSubmitDTO);

        return Result.success(vo);
    }

    /**
     * 订单支付
     *  TODO: websocket 双向通信
     */
    @PutMapping("/payment")
    @ApiModelProperty("订单支付")
    public Result<OrderPaymentVO> orderPayment(@RequestBody OrderPaymentDTO orderPaymentDTO) {
        log.info("订单支付: {}", orderPaymentDTO);

        // TODO：模拟交易成功 解决下单成功提醒
        OrderPaymentVO vo = orderService.orderPayment(orderPaymentDTO);

        // TODO: 支付成功后的 对订单的修改、以及WebSocket长连接相应 都可以进行异步处理
//        orderService.paySuccess(orderPaymentDTO.getOrderNumber());
        rabbitTemplate.convertAndSend("order.direct", "pay.success", orderPaymentDTO.getOrderNumber());

        log.info("生成预支付交易单：{}", vo);
        return Result.success(vo);
    }

    /**
     * 查询订单详情
     */
    @GetMapping("/orderDetail/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> queryOrderDetail(@PathVariable Long id) {
        log.info("查询订单详情: {}", id);

        OrderVO vo = orderService.queryOrderDetail(id);

        return Result.success(vo);
    }

    /**
     * 历史订单查询: 分页查询
     */
    @GetMapping("/historyOrders")
    @ApiOperation("历史订单查询")
    public Result<PageResult> queryHistoryOrders(HistoryOrdersDTO historyOrdersDTO) {
        log.info("查询历史订单: {}", historyOrdersDTO);

        PageResult page = orderService.queryHistoryOrders(historyOrdersDTO);

        return Result.success(page);
    }

    /**
     * 再来一单
     */
    @PostMapping("/repetition/{id}")
    @ApiModelProperty("再来一单")
    public Result repetitionOrder(@PathVariable Long id) {
        log.info("再来一单: {}", id);

        orderService.repetitionOrder(id);

        return Result.success();
    }

    /**
     * 取消订单
     */
    @PutMapping("/cancel/{id}")
    @ApiOperation("取消订单")
    public Result cancelOrder(@PathVariable Long id){
        log.info("取消订单: {}", id);

        orderService.cancelOrder(id);

        return Result.success();
    }

    /**
     * 催单
     */
    @GetMapping("/reminder/{id}")
    @ApiOperation("催单")
    public Result reminderOrder(@PathVariable Long id){
        log.info("催单: {}", id);

        orderService.reminderOrder(id);

        return Result.success();
    }
}
