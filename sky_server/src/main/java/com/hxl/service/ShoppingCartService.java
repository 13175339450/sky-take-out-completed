package com.hxl.service;

import com.hxl.dto.ShoppingCartAddOrSubDTO;
import com.hxl.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    /**
     * 添加购物车的相关接口
     * @param shoppingCartAddOrSubDTO 封装商品基本信息
     */
    void addShoppingCart(ShoppingCartAddOrSubDTO shoppingCartAddOrSubDTO);

    /**
     * 查看当前用户的购物车
     * @return 返回当前用户车的购物车信息
     */
    List<ShoppingCart> catShoppingCart();

    /**
     * 删除购物车的一个商品
     * @param shoppingCartAddOrSubDTO 删除的商品的基本信息
     */
    void subShoppingCart(ShoppingCartAddOrSubDTO shoppingCartAddOrSubDTO);

    /**
     * 清空购物车
     */
    void cleanShoppingCart();
}
