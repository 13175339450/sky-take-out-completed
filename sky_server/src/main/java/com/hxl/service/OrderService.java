package com.hxl.service;

import com.hxl.dto.HistoryOrdersDTO;
import com.hxl.dto.OrderPaymentDTO;
import com.hxl.dto.OrderSearchDTO;
import com.hxl.dto.OrderSubmitDTO;
import com.hxl.entity.Orders;
import com.hxl.result.PageResult;
import com.hxl.vo.OrderPaymentVO;
import com.hxl.vo.OrderSubmitVO;
import com.hxl.vo.OrderVO;

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

    /**
     * 查询订单的详情信息
     * @param id 订单id唯一
     * @return 返回查询到的订单的数据
     */
    OrderVO queryOrderDetail(Long id);

    /**
     * 查询历史订单数据
     * @param historyOrdersDTO 历史订单分页条件
     * @return 返回查询结果
     */
    PageResult queryHistoryOrders(HistoryOrdersDTO historyOrdersDTO);

    /**
     * 再来一单
     * @param id 订单id
     */
    void repetitionOrder(Long id);

    /**
     * 取消订单
     * @param id 订单id
     */
    void cancelOrder(Long id);

    /**
     * 订单搜索 -- 分页
     * @param orderSearchDTO 查询条件
     * @return 分页数据
     */
    PageResult conditionSearch(OrderSearchDTO orderSearchDTO);

}
