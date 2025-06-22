package com.hxl.mapper;

import com.github.pagehelper.Page;
import com.hxl.annotation.AutoFill;
import com.hxl.entity.SetMeal;
import com.hxl.enumeration.OperationType;
import com.hxl.vo.SetMealPageVO;
import org.apache.ibatis.annotations.Select;

public interface SetMealMapper {
    /**
     * 根据category_id 查询绑定的 setMeal套餐的数量
     * @param categoryId 分类id
     * @return 返回绑定的套餐数量
     */
    @Select("select count(*) from set_meal where category_id = #{categoryId}")
    Integer getCategoryBindSetMeal(Long categoryId);

    /**
     * 新增套餐方法
     * @param setMeal 套餐数据
     * @return 1表示插入成功 0表示插入失败
     */
    @AutoFill(OperationType.INSERT)
    int insertSetMeal(SetMeal setMeal);

    /**
     * 套餐分页查询
     * @param setMeal 查询条件的封装
     * @return 返回分页数据
     */
    Page<SetMealPageVO> setMealPage(SetMeal setMeal);
}
