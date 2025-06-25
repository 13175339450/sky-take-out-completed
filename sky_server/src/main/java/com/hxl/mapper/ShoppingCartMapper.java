package com.hxl.mapper;

import com.hxl.dto.ShoppingCartAddOrSubDTO;
import com.hxl.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartMapper {

    /**
     * 根据条件 动态查询是否存在 购物车数据
     * @param shoppingCartAddOrSubDTO 查询条件
     * @return 返回购物车记录数据
     */
    ShoppingCart queryShoppingCartExist(ShoppingCartAddOrSubDTO shoppingCartAddOrSubDTO);

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

    /**
     * 根据用户id查询当前用户的购物车信息
     * @param userId 用户id
     * @return 返回当前用户的购物车信息
     */
    List<ShoppingCart> queryShoppingCartByUserId(Long userId);

    /**
     * 动态条件查询商品数量
     * @param shoppingCartAddOrSubDTO 查询条件
     * @return 返回商品数量
     */
    int getShoppingNumber(ShoppingCartAddOrSubDTO shoppingCartAddOrSubDTO);

    /**
     * 动态删除购物车信息
     * @param shoppingCart 删除条件实体类
     */
    int deleteShoppingCart(ShoppingCart shoppingCart);

    /**
     * 清空购物车
     * @param userId
     */
    int cleanShoppingCart(Long userId);

    /**
     * 批量插入购物车
     * @param shoppingCarts 购物车数据集合
     * @return 返回插入成功的数量
     */
    int insertShoppingCartBatch(List<ShoppingCart> shoppingCarts);
}
