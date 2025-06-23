package com.hxl.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
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

        // 设置 key 的序列化器为 StringRedisSerializer
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        // 设置 value 的序列化器为 JSON（可选，根据需求）
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }
}
