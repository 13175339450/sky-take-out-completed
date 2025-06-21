package com.hxl.mapper;

import com.github.pagehelper.Page;
import com.hxl.annotation.AutoFill;
import com.hxl.dto.DishPageDTO;
import com.hxl.entity.Dish;
import com.hxl.enumeration.OperationType;
import com.hxl.vo.DishPageVO;
import org.apache.ibatis.annotations.Select;

public interface DishMapper {
    /**
     * 根据 category_id 查询 绑定的dish的数量
     * @param categoryId 分类id
     * @return 返回的是该分类绑定的菜品数量
     */
    @Select("select count(*) from dish where category_id = #{categoryId}")
    Integer getCategoryBindDishAmount(Long categoryId);

    /**
     * 动态分页查询 dish
     * @param dish 菜品表对应的实体类
     * @return 返回分页数据
     */
    Page<DishPageVO> dishDynamicPage(Dish dish);

    /**
     * 通用的更新dish的方法
     * @param dish 封装菜品信息的实体类
     * @return 0表示更新失败 反正更新成功
     */
    @AutoFill(OperationType.UPDATE)
    int updateDish(Dish dish);

    /**
     * 往菜品表里插入一条信息
     * @param dish 菜品数据的包装类
     */
    @AutoFill(OperationType.INSERT)
    int insertDish(Dish dish);

    /**
     * 通用的查询菜品信息的方法
     * @param dish 封装查询条件的菜品实体类
     * @return 返回查询的结果
     */
    Dish queryDish(Dish dish);
}
