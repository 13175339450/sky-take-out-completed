package com.hxl.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("查询订单管理数据接口的VO")
public class OrderOverViewVO implements Serializable {

    @ApiModelProperty("全部订单")
    private Integer allOrders;

    @ApiModelProperty("已取消数量")
    private Integer cancelledOrders;

    @ApiModelProperty("已完成数量")
    private Integer completedOrders;

    @ApiModelProperty("待派送数量")
    private Integer deliveredOrders;

    @ApiModelProperty("待接单数量")
    private Integer waitingOrders;
}
