package com.hxl.mapper;

import com.github.pagehelper.Page;
import com.hxl.dto.OrderSearchDTO;
import com.hxl.entity.Orders;
import com.hxl.vo.OrderStatisticsVO;
import com.hxl.vo.OrderVO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderMapper {
    /**
     * 插入一条订单数据
     * @param orders 订单信息
     */
    int insertOrder(Orders orders);

    /**
     * 根据订单id 更新订单表的动态update方法
     */
    @Update("update orders set status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{check_out_time} where id = #{id}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, Long id);

    /**
     * 更新方法
     * @param orders
     */
    void update(Orders orders);

    /**
     * 根据订单号查询订单id
     * @param outTradeNo
     * @return
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String outTradeNo);

    /**
     * 查询订单的基本信息
     * @param id 订单id
     * @return 返回订单的基本信息
     */
    @Select("select * from orders where id = #{id}")
    Orders queryOrderById(Long id);

    /**
     * 分页查询历史订单数据
     * @param orders 条件封装
     * @return 返回分页查询数据
     */
   Page<Orders> historyOrdersPage(Orders orders);

    /**
     * 条件查询 -- 管理端分页
     * @param orderSearchDTO 分页条件
     * @return 分页结果
     */
    Page<OrderVO> conditionSearch(OrderSearchDTO orderSearchDTO);

    /**
     * 获取各个状态的订单数量
     * @return 各个状态订单数量的实体类
     */
    OrderStatisticsVO getAnyOrderStatusNumber();

    /**
     * 查询超时订单的id
     * @param deadline 超时时间的晚
     * @return 超时的订单id
     */
    List<Long> queryPayTimeOutOrder(LocalDateTime deadline);


    /**
     * 批量修改订单
     * @param orderIds 订单id集合
     * @param order 订单修改的数据
     */
    int updateOrderBatch(List<Long> orderIds, Orders order);

    /**
     * 获取显示派送中但已完成的订单
     * @param status 派送状态
     * @return 返回订单id集合
     */
    @Select("select id from orders where status = #{status}")
    List<Long> queryDeliveryOrder(Integer status);
}
