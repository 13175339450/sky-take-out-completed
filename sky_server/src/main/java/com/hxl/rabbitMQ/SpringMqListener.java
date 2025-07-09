package com.hxl.rabbitMQ;

import com.hxl.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TODO: 利用注解的方式创建 交换机和队列 并且绑定关系
 */
@Component
@Slf4j
public class SpringMqListener {

    @Autowired
    private OrderService orderService;

    /**
     * 监听订单支付成功后的消息队列
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "order.pay.queue", durable = "true"),
            exchange = @Exchange(name = "order.direct"),
            key = "pay.success"
    ))
    public void paySuccess(String orderNumber) {//传入订单号
        log.info("==============监听到订单支付成功后的消息==================");
        //根据订单id 修改订单信息
        orderService.paySuccess(orderNumber);
    }

}
