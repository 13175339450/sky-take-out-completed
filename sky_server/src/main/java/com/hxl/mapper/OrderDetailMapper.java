package com.hxl.mapper;

import com.hxl.entity.OrderDetail;

import java.util.List;

public interface OrderDetailMapper {
    /**
     * 批量插入多条订单明细
     * @param orderDetailList 订单明细信息的集合
     */
    int insertOrderDetailBatch(List<OrderDetail> orderDetailList);
}
