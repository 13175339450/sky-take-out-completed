package com.hxl.mapper;

import com.hxl.entity.SetMealDish;

import java.util.List;

public interface SetMealDishMapper {
    /**
     * 根据菜品id 集合 批量获取菜品绑定的套餐数量
     * @param ids 菜品id集合
     * @return 返回菜品绑定的套餐数量
     */
    Integer getSetMealBindDishAmountBatch(List<Long> ids);

    /**
     * 批量插入 套餐菜品信息
     * @param setMealDishes 套餐和菜品关联的信息集合
     * @return 0表示插入失败 否则表示插入成功的记录数
     */
    int insertSetMealDish(List<SetMealDish> setMealDishes);
}
