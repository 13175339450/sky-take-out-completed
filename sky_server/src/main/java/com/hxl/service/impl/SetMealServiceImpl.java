package com.hxl.service.impl;

import com.hxl.constant.StatusConstant;
import com.hxl.dto.SetMealAddDTO;
import com.hxl.entity.SetMeal;
import com.hxl.entity.SetMealDish;
import com.hxl.mapper.SetMealDishMapper;
import com.hxl.mapper.SetMealMapper;
import com.hxl.service.SetMealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;

    /**
     * 新增套餐
     */
    @Override
    @Transactional
    public void addSetMeal(SetMealAddDTO setMealAddDTO) {
        //封装实体类
        SetMeal setMeal = new SetMeal();
        BeanUtils.copyProperties(setMealAddDTO, setMeal);

        //补充状态信息
        setMeal.setStatus(StatusConstant.SET_MEAL_DEFAULT_STATUS);

        //调用插入方法 需要主键回显 套餐id
        setMealMapper.insertSetMeal(setMeal);

        //获取 回显后的 套餐id
        Long setMealId = setMeal.getId();

        //批量插入 套餐包含的菜品信息
        List<SetMealDish> setMealDishes = setMealAddDTO.getSetmealDishes();

        //没有菜品信息
        if (setMealDishes == null || setMealDishes.isEmpty()) return;

        for (SetMealDish setMealDish : setMealDishes) {
            //插入套餐id
            setMealDish.setSetmealId(setMealId);
        }

        //批量插入
        setMealDishMapper.insertSetMealDish(setMealDishes);
    }
}
