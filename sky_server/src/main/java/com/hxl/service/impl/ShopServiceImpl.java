package com.hxl.service.impl;

import com.hxl.constant.RedisNameConstant;
import com.hxl.service.ShopService;
import com.hxl.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 设置店铺状态
     */
    @Override
    public void setShopStatus(Integer status) {
        //利用存储在Redis里 设置店铺 key value
//        redisTemplate.opsForValue().set(RedisNameConstant.SHOP_STATUS, status);
        redisCacheUtil.addCache(RedisNameConstant.SHOP_STATUS, status);
    }

    /**
     * 获取店铺营业状态
     */
    @Override
    public Integer getShopStatus() {
//        return (Integer) redisTemplate.opsForValue().get(RedisNameConstant.SHOP_STATUS);
        String shopStatus = stringRedisTemplate.opsForValue().get(RedisNameConstant.SHOP_STATUS);
        //TODO: bug 当第一次连接redis时，查询到status为null 需要将状态设置为0 避免异常
        shopStatus = shopStatus == null ? "0" : shopStatus;
        return Integer.valueOf(shopStatus);
    }
}
