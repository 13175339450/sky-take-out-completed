package com.hxl.service;

import com.hxl.dto.SetMealAddDTO;
import com.hxl.dto.SetMealPageDTO;
import com.hxl.result.PageResult;

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

}
