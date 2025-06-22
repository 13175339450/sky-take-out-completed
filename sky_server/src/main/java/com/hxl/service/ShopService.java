package com.hxl.service;

public interface ShopService {
    /**
     * 设置店铺状态
     * @param status 设置的店铺状态
     */
    void setShopStatus(Integer status);

    /**
     * 获取店铺营业状态
     * @return
     */
    Integer getShopStatus();
}
