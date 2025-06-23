package com.hxl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hxl.constant.MessageConstant;
import com.hxl.constant.StatusConstant;
import com.hxl.dto.SetMealAddDTO;
import com.hxl.dto.SetMealEditDTO;
import com.hxl.dto.SetMealPageDTO;
import com.hxl.entity.SetMeal;
import com.hxl.entity.SetMealDish;
import com.hxl.exception.DeleteFailException;
import com.hxl.mapper.SetMealDishMapper;
import com.hxl.mapper.SetMealMapper;
import com.hxl.result.PageResult;
import com.hxl.service.SetMealService;
import com.hxl.vo.DishItemVO;
import com.hxl.vo.SetMealPageVO;
import com.hxl.vo.SetMealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    /**
     * 套餐分页查询
     */
    @Override
    public PageResult SetMealPage(SetMealPageDTO setMealPageDTO) {
        //设置分页数据
        PageHelper.startPage(setMealPageDTO.getPage(), setMealPageDTO.getPageSize());

        //实体类封装查询条件
        SetMeal setMeal = new SetMeal();
        BeanUtils.copyProperties(setMealPageDTO, setMeal);

        //进行分页查询 特殊的VO类
        Page<SetMealPageVO> page = setMealMapper.setMealPage(setMeal);

        //获取对应的结果
        long total = page.getTotal();
        List<SetMealPageVO> records = page.getResult();

        //结果返回
        return new PageResult(total, records);
    }

    /**
     * 套餐起售、停售
     */
    @Override
    public void startOrStopSetMeal(Integer status, Long id) {
        //封装实体类
        SetMeal setMeal = SetMeal.builder().status(status).id(id).build();

        //调用通用的update方法
        setMealMapper.updateSetMeal(setMeal);
    }

    /**
     * 根据套餐id查询套餐信息: 套餐信息 + 套餐和菜品的关联
     */
    @Override
    public SetMealVO querySetMealById(Long id) {
        //根据id查询套餐信息
        SetMealVO setMealVO = setMealMapper.querySetMealById(id);

        //根据套餐id查询 套餐菜品信息
        List<SetMealDish> setMealDishes = setMealDishMapper.querySetMealDishBySetmealId(id);

        setMealVO.setSetmealDishes(setMealDishes);
        return setMealVO;
    }

    /**
     * 修改套餐: 开启事务
     *      1.更新套餐表信息
     *      2.删除原有的该套餐和菜品关联信息
     *      3.插入新的套餐和菜品的关联信息
     */
    @Override
    @Transactional
    public void editSetMeal(SetMealEditDTO setMealEditDTO) {
        //更新套餐信息
        SetMeal setMeal = new SetMeal();
        BeanUtils.copyProperties(setMealEditDTO, setMeal);
        setMealMapper.updateSetMeal(setMeal);//调用通用的更新方法

        //根据套餐id 删除原有的套餐和菜品的信息
        Long setMealId = setMealEditDTO.getId();

        //调用批量删除方法
        List<Long> ids = new ArrayList<>();
        ids.add(setMealId);
        setMealDishMapper.deleteSetMealDishBySetMealId(ids);

        //插入新的套餐菜品信息
        List<SetMealDish> setmealDishes = setMealEditDTO.getSetmealDishes();

        //TODO: 赋值 套餐id值
        for (SetMealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setMealId);
        }

        if (setmealDishes == null || setmealDishes.isEmpty()) return;

        setMealDishMapper.insertSetMealDish(setmealDishes);
    }

    /**
     * 批量套餐
     */
    @Override
    @Transactional
    public void deleteSetMealBatch(List<Long> ids) {
        //批量查询起售中的套餐数量
        Integer amount = setMealMapper.getStartSetMealAmountBatch(ids);

        if (amount > 0){
            //起售中的套餐不能删除
            throw new DeleteFailException(MessageConstant.DELETE_FAIL_START_SET_MEAL);
        }

        //删除套餐表里的套餐数据
        setMealMapper.deleteSetMealBatch(ids);

        //删除套餐菜品表里的数据
        setMealDishMapper.deleteSetMealDishBySetMealId(ids);
    }

    /**
     * 根据分类id查询套餐信息
     */
    @Override
    public List<SetMeal> querySetMealByCategoryId(Long categoryId) {

        //根据分类id查询套餐信息 启售中的套餐
        return setMealMapper.querySetMealByCategoryId(categoryId);
    }

    /**
     * 根据套餐id 查询菜品信息 多表联查
     */
    @Override
    public List<DishItemVO> queryDishBySetMealId(Long id) {
        //根据套餐id查询菜品信息 可以包括停售的
        return setMealDishMapper.queryDishBySetMealId(id);
    }
}
