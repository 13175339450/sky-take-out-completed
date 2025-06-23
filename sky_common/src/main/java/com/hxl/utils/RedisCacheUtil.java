package com.hxl.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxl.constant.RedisNameConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * redis缓存操作的工具类
 */
@Component
@Slf4j
public class RedisCacheUtil {

    //将java对象进行序列化
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 添加缓存
     */
    public void addCache(String key, Object value){
        //将value进行序列化
        try {
            String json = objectMapper.writeValueAsString(value);
            stringRedisTemplate.opsForValue().set(key, json);
            log.info("已添加redis缓存:key为 {}", key);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除特定缓存
     */
    public void deleteCache(String key){
        log.info("已删除redis缓存:key为 {}", key);
        stringRedisTemplate.delete(key);
    }

    /**
     * 清空缓存
     */
    public void flushCache(String preName){
        log.info("已清空redis缓存:前缀为 {}", preName);
        String pattern = preName + "*";
        Set<String> keys = stringRedisTemplate.keys(pattern);
        stringRedisTemplate.delete(keys);
    }
}
