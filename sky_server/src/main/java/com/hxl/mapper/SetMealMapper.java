package com.hxl.mapper;

import com.github.pagehelper.Page;
import com.hxl.annotation.AutoFill;
import com.hxl.entity.SetMeal;
import com.hxl.enumeration.OperationType;
import com.hxl.vo.SetMealOverViewVO;
import com.hxl.vo.SetMealPageVO;
import com.hxl.vo.SetMealVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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

    /**
     * 调用通用的update方法
     * @param setMeal 更新条件和更新数据
     * @return 1表示更新成功 0表示更新失败
     */
    @AutoFill(OperationType.UPDATE)
    int updateSetMeal(SetMeal setMeal);

    /**
     * 根据套餐id 查询套餐信息 多表联查
     * @return
     */
    SetMealVO querySetMealById(Long id);

    /**
     * 批量查询启售中套餐的数量
     * @param ids 套餐id集合
     * @return 返回起售套餐数量
     */
    int getStartSetMealAmountBatch(List<Long> ids);

    /**
     * 批量删除套餐信息
     * @param ids 套餐id集合
     * @return 返回删除的行数
     */
    int deleteSetMealBatch(List<Long> ids);

    /**
     * 用户端 根据分类id查询套餐信息
     * @param categoryId 分类id
     * @return 返回启售中的套餐信息
     */
    @Select("select * from set_meal where status = 1 and category_id = #{categoryId}")
    List<SetMeal> querySetMealByCategoryId(Long categoryId);

    /**
     * 根据套餐id查询套餐基本信息
     * @param setmealId 套餐id
     * @return 返回套餐表的基本数据
     */
    @Select("select * from set_meal where id = #{setmealId}")
    SetMeal querySetMealInfoById(Long setmealId);

    /**
     * 查询套餐总览
     * @param map 查询条件封装
     * @return 返回套餐总览信息
     */
    SetMealOverViewVO overviewSetMeals(Map map);

    /**
     * 动态查询SetMeal
     */
    List<SetMeal> querySetMeal(SetMeal setMeal);
}
