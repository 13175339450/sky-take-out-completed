package com.hxl.task;

import com.hxl.constant.MessageConstant;
import com.hxl.entity.Orders;
import com.hxl.mapper.OrderMapper;
import com.hxl.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 每隔一分钟 清理超时订单
     */
    @Scheduled(cron = "0 * * * * ?")//每分钟触发
    public void dealPayTimeOutOrder(){
        log.info("正在处理超时订单...");
        //获取当前时间的前15分钟
        LocalDateTime deadline = LocalDateTime.now().plusMinutes(-15);

        //查询出来所有超时的订单id
        List<Long> orderIds = orderMapper.queryPayTimeOutOrder(deadline);

        //封装修改的内容
        Orders order = Orders.builder()
                .status(Orders.CANCELLED)//订单已取消
                .payStatus(Orders.REFUND)//订单退款
                .cancelReason(MessageConstant.PAY_TIME_OUT)//支付超时
                .cancelTime(LocalDateTime.now()).build();

        if (orderIds != null && !orderIds.isEmpty()){
            //进行批量修改
            orderMapper.updateOrderBatch(orderIds, order);
        }
    }

    /**
     * 处理显示派送中但已完成的订单
     */
    @Scheduled(cron = "0 0 1 * * ? ")//每天凌晨一点触发
    public void dealAlreadyDeliveryOrder(){
        log.info("处理显示派送中但已完成的订单...");

        Integer status = Orders.DELIVERY_IN_PROGRESS;//派送中的状态

        //获取所有 显示派送中 的订单id
        List<Long> orderIds = orderMapper.queryDeliveryOrder(status);

        //封装修改的内容
        Orders order = Orders.builder()
                .status(Orders.COMPLETED)//状态
                .deliveryTime(LocalDateTime.now()).build();

        if (orderIds != null && !orderIds.isEmpty()){
            //批量修改
            orderMapper.updateOrderBatch(orderIds, order);
        }
    }


}
