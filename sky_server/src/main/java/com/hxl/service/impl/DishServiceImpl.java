package com.hxl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hxl.constant.StatusConstant;
import com.hxl.dto.DishDTO;
import com.hxl.dto.DishPageDTO;
import com.hxl.entity.Dish;
import com.hxl.entity.DishFlavor;
import com.hxl.mapper.DishFlavorMapper;
import com.hxl.mapper.DishMapper;
import com.hxl.result.PageResult;
import com.hxl.service.DishService;
import com.hxl.vo.DishPageVO;
import com.hxl.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * 菜品分页查询
     */
    @Override
    public PageResult dishPage(DishPageDTO dishPageDTO) {
        //设置分页数据
        PageHelper.startPage(dishPageDTO.getPage(), dishPageDTO.getPageSize());

        //进行分页查询 动态sql
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishPageDTO, dish);

        //连结 dish 和 category两个表
        Page<DishPageVO> page = dishMapper.dishDynamicPage(dish);

        //获取数据
        long total = page.getTotal();
        List<DishPageVO> records = page.getResult();

        return new PageResult(total, records);
    }

    /**
     * 菜品启售、停售
     */
    @Override
    public void startOrStopDish(Integer status, Long id) {
        //封装数据到实体类
        Dish dish = Dish.builder().status(status).id(id).build();

        //调用通用的更新方法 动态sql
        int row = dishMapper.updateDish(dish);
    }

    /**
     * 新增菜品
     */
    @Override
    @Transactional //涉及多个表的关联操作 需要开始事务
    public void addDish(DishDTO dishDTO) {
        //利用实体类封装
        Dish dish = new Dish();

        //获取菜品相关信息
        BeanUtils.copyProperties(dishDTO, dish);

        //补充相关数据
        dish.setStatus(StatusConstant.DISH_DEFAULT_STATUS);

        //往菜品表里插入菜品信息 并且进行主键回显
        dishMapper.insertDish(dish);

        //获取主键回显的 菜品id
        Long dishId = dish.getId();

        //获取口味数据 并且绑定菜品id 插入菜品口味表中
        List<DishFlavor> flavors = dishDTO.getFlavors();

        //没有口味数据 直接退出
        if (flavors == null || flavors.isEmpty()) return;

        //给口味数据赋值 菜品id
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishId);
        }

        //调用 批量插入 菜品口味表的方法
        dishFlavorMapper.insertDishFlavorBatch(flavors);
    }

    /**
     * 根据id查询菜品信息
     */
    @Override
    public DishVO queryDishById(Long id) {
        //结果类
        DishVO dishVO = new DishVO();

        //创建通用查询的实体类
        Dish dish = Dish.builder().id(id).build();

        //使用菜品表的
        dish = dishMapper.queryDish(dish);

        //属性拷贝 填充菜品相关数据
        BeanUtils.copyProperties(dish, dishVO);

        //根据菜品id 查询菜品对应的口味信息
        List<DishFlavor> flavors = dishFlavorMapper.queryFlavorByDishId(id);

        //口味数据填充
        dishVO.setFlavors(flavors);

        return dishVO;
    }

    /**
     * 修改菜品
     *    TODO:为了避免复杂的数据变更业务
     *         在修改菜品时，修改菜品信息 update
     *              删除原有口味信息，重新插入新的口味信息 delete + insert
     *                  必须开启事务
     */
    @Override
    @Transactional
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //调用通用的update方法 更新菜品信息
        dishMapper.updateDish(dish);

        //根据菜品id 删除原有的口味信息
        dishFlavorMapper.deleteFlavor(dishDTO.getId());

        //插入新的口味信息
        List<DishFlavor> flavors = dishDTO.getFlavors();

        //判断是否有口味数据
        if (flavors == null || flavors.isEmpty()) return;

        Long dishId = dish.getId();
        //TODO:bug修复 口味flavors里的dishId需要手动赋值
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishId);
        }

        //调用批量插入口味信息的方法
        dishFlavorMapper.insertDishFlavorBatch(flavors);
    }
}
