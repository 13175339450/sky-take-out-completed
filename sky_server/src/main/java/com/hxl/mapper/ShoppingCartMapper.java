package com.hxl.mapper;

import com.hxl.dto.ShoppingCartAddDTO;
import com.hxl.entity.ShoppingCart;

public interface ShoppingCartMapper {

    /**
     * 根据条件 动态查询是否存在 购物车数据
     * @param shoppingCartAddDTO 查询条件
     * @return 返回购物车记录数据
     */
    ShoppingCart queryShoppingCartExist(ShoppingCartAddDTO shoppingCartAddDTO);

    /**
     * 执行通用的更新方法
     * @param shoppingCart 新的数据
     * @return 1表示更新成功 0表示更新失败
     */
    int updateShoppingCart(ShoppingCart shoppingCart);

    /**
     * 插入一条购物车数据
     * @param shoppingCart 购物车的内容
     */
    int insertShoppingCart(ShoppingCart shoppingCart);
}
