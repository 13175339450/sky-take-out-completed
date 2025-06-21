package com.hxl.mapper;

import com.hxl.entity.DishFlavor;

import java.util.List;

public interface DishFlavorMapper {
    /**
     * 批量插入菜品口味信息
     * @param flavors 菜品口味信息集合
     * @return 返回插入结果
     */
    int insertDishFlavorBatch(List<DishFlavor> flavors);
}
