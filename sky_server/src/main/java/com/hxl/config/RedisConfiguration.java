package com.hxl.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Component
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplateCreate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始创建redis模板对象");

        RedisTemplate redisTemplate = new RedisTemplate();

        //设置redis连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //设置redis key的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
