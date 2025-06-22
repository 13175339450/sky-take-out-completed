package com.hxl.mapper;

import com.hxl.entity.SetMealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 根据套餐id查询套餐菜品信息
     * @param id 套餐id
     * @return 返回套餐菜品信息
     */
    @Select("select * from set_meal_dish where setmeal_id = #{id}")
    List<SetMealDish> querySetMealDishBySetmealId(Long id);

    /**
     * 根据套餐id集合 批量删除套餐菜品关联表
     * @param ids 套餐id
     * return 0表示删除失败 反之为删除的行数
     */
    int deleteSetMealDishBySetMealId(List<Long> ids);
}
