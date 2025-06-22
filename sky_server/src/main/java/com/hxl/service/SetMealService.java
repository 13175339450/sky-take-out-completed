package com.hxl.service;

import com.hxl.dto.SetMealAddDTO;

public interface SetMealService {
    /**
     * 新增套餐
     * @param setMealAddDTO 封装套餐相关信息
     */
    void addSetMeal(SetMealAddDTO setMealAddDTO);
}
