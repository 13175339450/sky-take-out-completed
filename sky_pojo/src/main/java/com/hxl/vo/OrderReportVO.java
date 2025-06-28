package com.hxl.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("订单统计接口的VO")
public class OrderReportVO implements Serializable {

    @ApiModelProperty("日期列表")
    private String dateList;

    @ApiModelProperty("订单完成率")
    private Double orderCompletionRate;

    @ApiModelProperty("订单数列表")
    private String orderCountList;

    @ApiModelProperty("订单总数")
    private Integer totalOrderCount;

    @ApiModelProperty("有效订单数")
    private Integer validOrderCount;

    @ApiModelProperty("有效订单数列表")
    private String validOrderCountList;
}
