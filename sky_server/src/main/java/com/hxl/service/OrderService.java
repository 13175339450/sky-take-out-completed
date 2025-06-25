package com.hxl.service;

import com.hxl.dto.OrderPaymentDTO;
import com.hxl.dto.OrderSubmitDTO;
import com.hxl.entity.Orders;
import com.hxl.vo.OrderPaymentVO;
import com.hxl.vo.OrderSubmitVO;

public interface OrderService {
    /**
     * 提交当前用户的订单
     * @param orderSubmitDTO 订单信息
     * @return 返回提交订单的视图
     */
    OrderSubmitVO submitOrder(OrderSubmitDTO orderSubmitDTO);

    /**
     * 订单支付相关接口
     * @param orderPaymentDTO 支付信息
     * @return 返回支付后的信息
     */
    OrderPaymentVO orderPayment(OrderPaymentDTO orderPaymentDTO);

    /**
     * 支付成功
     * @param orderNumber 订单号
     */
    void paySuccess(String orderNumber);
}
