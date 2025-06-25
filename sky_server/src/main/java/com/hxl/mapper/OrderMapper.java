package com.hxl.mapper;

import com.hxl.entity.Orders;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

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
}
