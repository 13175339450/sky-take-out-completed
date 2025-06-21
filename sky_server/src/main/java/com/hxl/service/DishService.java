package com.hxl.service;

import com.hxl.dto.DishAddDTO;
import com.hxl.dto.DishPageDTO;
import com.hxl.result.PageResult;

public interface DishService {
    /**
     * 菜品分页查询
     * @param dishPageDTO 分页查询的数据的封装类
     * @return 菜品分页查询结果的封装
     */
    PageResult dishPage(DishPageDTO dishPageDTO);

    /**
     * 菜品启售、停售
     * @param status 菜品状态
     * @param id 菜品id
     */
    void startOrStopDish(Integer status, Long id);

    /**
     * 新增菜品
     * @param dishAddDTO 新增菜品数据的DTO
     */
    void addDish(DishAddDTO dishAddDTO);
}
