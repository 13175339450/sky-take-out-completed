package com.hxl.mapper;

import java.util.List;

public interface SetMealDishMapper {
    /**
     * 根据菜品id 集合 批量获取菜品绑定的套餐数量
     * @param ids 菜品id集合
     * @return 返回菜品绑定的套餐数量
     */
    Integer getSetMealBindDishAmountBatch(List<Long> ids);
}
