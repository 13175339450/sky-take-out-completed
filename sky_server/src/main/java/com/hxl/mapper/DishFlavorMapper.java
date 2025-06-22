package com.hxl.mapper;

import com.hxl.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface DishFlavorMapper {
    /**
     * 批量插入菜品口味信息
     * @param flavors 菜品口味信息集合
     * @return 返回插入结果
     */
    int insertDishFlavorBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品id查询口味信息
     * @param dishId 菜品id
     * @return 返回口味表
     */
    List<DishFlavor> queryFlavorByDishId(Long dishId);

    @Delete("delete from dish_flavor where dish_id = #{id}")
    int deleteFlavor(Long id);

    /**
     * 批量删除口味信息
     * @param ids 菜品id
     * @return 操作成功的数量
     */
    int deleteFlavorBatchByDishId(List<Long> ids);
}
