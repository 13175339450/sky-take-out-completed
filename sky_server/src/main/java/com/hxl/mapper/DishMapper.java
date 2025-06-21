package com.hxl.mapper;

import org.apache.ibatis.annotations.Select;

public interface DishMapper {
    /**
     * 根据 category_id 查询 绑定的dish的数量
     * @param categoryId 分类id
     * @return 返回的是该分类绑定的菜品数量
     */
    @Select("select count(*) from dish where category_id = #{categoryId}")
    Integer getCategoryBindDishAmount(Long categoryId);
}
