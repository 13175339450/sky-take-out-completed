package com.hxl.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    //创建被Spring管理的RabbitTemplate
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        return new RabbitTemplate();
//    }

    //注入消息转换器
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
