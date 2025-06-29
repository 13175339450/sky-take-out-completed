package com.hxl.service;

import com.hxl.vo.BusinessDataVO;
import com.hxl.vo.DishOverViewVO;
import com.hxl.vo.OrderOverViewVO;
import com.hxl.vo.SetMealOverViewVO;

public interface WorkspaceService {
    /**
     * 查询今日运营数据
     * @return 返回今日的一些运营数据信息
     */
    BusinessDataVO businessData();

    /**
     * 查询订单管理数据
     * @return 返回订单管理数据的内容
     */
    OrderOverViewVO overviewOrders();

    /**
     * 查询菜品总览
     * @return 返回菜品的总览数据
     */
    DishOverViewVO overviewDishes();

    /**
     * 查询套餐总览
     * @return 返回套餐总览数据
     */
    SetMealOverViewVO overviewSetMeals();
}
