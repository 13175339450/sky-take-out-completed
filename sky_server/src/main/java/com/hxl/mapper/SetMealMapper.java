package com.hxl.mapper;

import org.apache.ibatis.annotations.Select;

public interface SetMealMapper {
    /**
     * 根据category_id 查询绑定的 setMeal套餐的数量
     * @param categoryId 分类id
     * @return 返回绑定的套餐数量
     */
    @Select("select count(*) from set_meal where category_id = #{categoryId}")
    Integer getCategoryBindSetMeal(Long categoryId);
}
