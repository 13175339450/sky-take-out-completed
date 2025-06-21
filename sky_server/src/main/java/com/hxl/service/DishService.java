package com.hxl.service;

import com.hxl.dto.DishDTO;
import com.hxl.dto.DishPageDTO;
import com.hxl.result.PageResult;
import com.hxl.vo.DishVO;

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
     * @param dishDTO 新增菜品数据的DTO
     */
    void addDish(DishDTO dishDTO);

    /**
     * 根据id查询菜品信息
     * @param id 菜品id
     * @return 返回菜品信息的VO
     */
    DishVO queryDishById(Long id);

    /**
     * 修改菜品
     * @param dishDTO 新的菜品数据
     */
    void updateDish(DishDTO dishDTO);
}
