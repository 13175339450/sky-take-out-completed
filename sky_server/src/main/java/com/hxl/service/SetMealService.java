package com.hxl.service;

import com.hxl.dto.SetMealAddDTO;
import com.hxl.dto.SetMealPageDTO;
import com.hxl.result.PageResult;
import com.hxl.vo.SetMealVO;

public interface SetMealService {
    /**
     * 新增套餐
     * @param setMealAddDTO 封装套餐相关信息
     */
    void addSetMeal(SetMealAddDTO setMealAddDTO);

    /**
     * 套餐分页查询
     * @param setMealPageDTO 分页数据的DTO实体类
     * @return 返回分页查询结果
     */
    PageResult SetMealPage(SetMealPageDTO setMealPageDTO);

    /**
     * 套餐起售、停售
     * @param status 套餐状态
     * @param id 套餐id
     */
    void startOrStopSetMeal(Integer status, Long id);

    /**
     * 根据id查询套餐信息
     * @param id 套餐id
     */
    SetMealVO querySetMealById(Long id);
}
