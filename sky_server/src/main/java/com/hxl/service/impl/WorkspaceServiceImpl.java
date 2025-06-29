package com.hxl.service.impl;

import com.hxl.constant.StatusConstant;
import com.hxl.entity.Orders;
import com.hxl.entity.SetMeal;
import com.hxl.mapper.DishMapper;
import com.hxl.mapper.OrderMapper;
import com.hxl.mapper.SetMealMapper;
import com.hxl.mapper.UserMapper;
import com.hxl.service.WorkspaceService;
import com.hxl.vo.BusinessDataVO;
import com.hxl.vo.DishOverViewVO;
import com.hxl.vo.OrderOverViewVO;
import com.hxl.vo.SetMealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetMealMapper setMealMapper;

    /**
     * 查询今日运营数据
     */
    @Override
    public BusinessDataVO businessData() {
        //封装今日的时间
        LocalDateTime begin = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        //查询今日的相关数据
        Map map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        map.put("status", Orders.COMPLETED);//已完成的订单
        //获取营业额
        BigDecimal turnover = orderMapper.turnoverStatistics(map);
        //解决空指针异常的问题
        turnover = turnover == null ? BigDecimal.valueOf(0.0) : turnover;
        //获取有效订单数
        Integer validOrderCount = orderMapper.getOrderCount(map);
        //获取总订单数
        map.put("status", null);
        Integer totalCount = orderMapper.getOrderCount(map);

        //计算订单完成率 TODO：判断非空和除0
        Double orderCompletionRate = totalCount == null || totalCount == 0.0 ? 0.0 : validOrderCount * 1.0 / totalCount;

        //计算平均客单价(有效订单数->客人数) TODO：判断非空和除0
        Double unitPrice = validOrderCount == null || validOrderCount == 0 ? 0.0 : turnover.doubleValue() / validOrderCount;

        //获取新增用户数
        Integer newUsers = userMapper.userStatistics(map);

        return new BusinessDataVO(newUsers, orderCompletionRate, turnover, unitPrice, validOrderCount);
    }

    /**
     * 查询订单管理数据
     */
    @Override
    public OrderOverViewVO overviewOrders() {
        //获取今日的开始时间和结束时间
        LocalDateTime begin = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        //使用sql聚合查询
        Map map = new HashMap<>();
        map.put("begin", begin);//开始时间
        map.put("end", end);//结束时间
        map.put("all", null);//全部
        map.put("cancelled", Orders.CANCELLED);//已取消
        map.put("completed", Orders.COMPLETED);//已完成
        map.put("delivered", Orders.CONFIRMED);//待派送 == 已接单
        map.put("waiting", Orders.TO_BE_CONFIRMED);//待接单

        return orderMapper.overviewOrders(map);
    }

    /**
     * 查询菜品总览
     */
    @Override
    public DishOverViewVO overviewDishes() {
        //封装查询数据
        Map map = new HashMap();
        map.put("discontinued", StatusConstant.DISCONTINUED);//已停售状态
        map.put("sold", StatusConstant.SOLD);//已起售状态

        return dishMapper.overviewDishes(map);
    }

    /**
     * 查询套餐总览
     */
    @Override
    public SetMealOverViewVO overviewSetMeals() {
        //封装查询条件
        Map map = new HashMap();
        map.put("discontinued", StatusConstant.DISCONTINUED);//已停售
        map.put("sold", StatusConstant.SOLD);//已起售

        return setMealMapper.overviewSetMeals(map);
    }
}
