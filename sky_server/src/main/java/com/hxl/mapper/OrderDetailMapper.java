package com.hxl.mapper;

import com.hxl.entity.OrderDetail;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderDetailMapper {
    /**
     * 批量插入多条订单明细
     * @param orderDetailList 订单明细信息的集合
     */
    int insertOrderDetailBatch(List<OrderDetail> orderDetailList);

    /**
     * 查询订单的详细信息
     * @param id 订单id
     * @return 返回订单详情的基本信息
     */
    @Select("select * from order_detail where order_id = #{id}")
    List<OrderDetail> queryOrderDetailByOrderId(Long id);
}
