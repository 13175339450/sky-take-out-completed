package com.hxl.controller.user;

import com.hxl.dto.OrderPaymentDTO;
import com.hxl.dto.OrderSubmitDTO;
import com.hxl.entity.Orders;
import com.hxl.result.Result;
import com.hxl.service.OrderService;
import com.hxl.vo.OrderPaymentVO;
import com.hxl.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderSubmitController")
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "用户下单相关的接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

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
     */
    @PutMapping("/payment")
    @ApiModelProperty("订单支付")
    public Result<OrderPaymentVO> orderPayment(@RequestBody OrderPaymentDTO orderPaymentDTO){
        log.info("订单支付: {}", orderPaymentDTO);

        OrderPaymentVO vo = orderService.orderPayment(orderPaymentDTO);

        //TODO：模拟交易成功 解决下单成功提醒
        orderService.paySuccess(orderPaymentDTO.getOrderNumber());

        log.info("生成预支付交易单：{}", vo);
        return Result.success(vo);
    }
}
