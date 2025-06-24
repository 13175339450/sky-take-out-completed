package com.hxl.service.impl;

import com.hxl.context.BaseContext;
import com.hxl.dto.ShoppingCartAddDTO;
import com.hxl.entity.Dish;
import com.hxl.entity.SetMeal;
import com.hxl.entity.ShoppingCart;
import com.hxl.mapper.DishMapper;
import com.hxl.mapper.SetMealMapper;
import com.hxl.mapper.ShoppingCartMapper;
import com.hxl.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetMealMapper setMealMapper;

    /**
     * 添加购物车的相关接口
     */
    @Override
    @Transactional
    public void addShoppingCart(ShoppingCartAddDTO shoppingCartAddDTO) {
        /* 根据 dishFlavor、dishId/setmealId、userId去数据库里查有没有存在的数据
            1.有则在原有的number上+1    2.没有则插入一条新的数据               */
        shoppingCartAddDTO.setUserId(BaseContext.getCurrentId());
        ShoppingCart shoppingCart = shoppingCartMapper.queryShoppingCartExist(shoppingCartAddDTO);
        //已有该记录
        if (shoppingCart != null) {
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            //执行通用更新操作 增加数量 (不能修改口味,因为口味是标识购物车唯一的标识之一)
            shoppingCartMapper.updateShoppingCart(shoppingCart);
        } else {
            //没有该记录，准备实体类，填充数据 并且插入新数据
            shoppingCart = ShoppingCart.builder()
                    .userId(BaseContext.getCurrentId())
                    .dishFlavor(shoppingCartAddDTO.getDishFlavor())
                    .creatTime(LocalDateTime.now())
                    .dishId(shoppingCartAddDTO.getDishId())
                    .setmealId(shoppingCartAddDTO.getSetmealId())
                    .number(1).build();

            //判断加入的是菜品还是套餐
            if (shoppingCartAddDTO.getDishId() != null) {
                Dish dish = Dish.builder().id(shoppingCartAddDTO.getDishId()).build();
                //是菜品 通用查询 返回一个数据
                List<Dish> dishes = dishMapper.queryDish(dish);
                //TODO: 手动赋值单价
                shoppingCart.setAmount(dishes.get(0).getPrice());
                BeanUtils.copyProperties(dishes.get(0), shoppingCart);

            } else {
                SetMeal setMeal = setMealMapper.querySetMealInfoById(shoppingCartAddDTO.getSetmealId());
                //TODO: 手动赋值单价
                shoppingCart.setAmount(setMeal.getPrice());
                BeanUtils.copyProperties(setMeal, shoppingCart);
            }
            //执行插入操作
            shoppingCartMapper.insertShoppingCart(shoppingCart);
        }
    }
}
