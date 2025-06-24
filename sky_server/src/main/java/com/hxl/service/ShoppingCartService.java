package com.hxl.service;

import com.hxl.dto.ShoppingCartAddDTO;

public interface ShoppingCartService {
    /**
     * 添加购物车的相关接口
     * @param shoppingCartAddDTO 封装商品基本信息
     */
    void addShoppingCart(ShoppingCartAddDTO shoppingCartAddDTO);
}
