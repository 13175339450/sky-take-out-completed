package com.hxl.service.impl;

import com.hxl.entity.Orders;
import com.hxl.mapper.OrderMapper;
import com.hxl.mapper.UserMapper;
import com.hxl.service.WorkspaceService;
import com.hxl.vo.BusinessDataVO;
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
}
