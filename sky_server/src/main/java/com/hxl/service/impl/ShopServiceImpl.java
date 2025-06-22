package com.hxl.service.impl;

import com.hxl.constant.RedisNameConstant;
import com.hxl.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置店铺状态
     */
    @Override
    public void setShopStatus(Integer status) {
        //利用存储在Redis里 设置店铺 key value
        redisTemplate.opsForValue().set(RedisNameConstant.SHOP_STATUS, status);
    }

    /**
     * 获取店铺营业状态
     */
    @Override
    public Integer getShopStatus() {
        return (Integer) redisTemplate.opsForValue().get(RedisNameConstant.SHOP_STATUS);
    }
}
