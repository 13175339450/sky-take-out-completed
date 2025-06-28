package com.hxl.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("查询今日运行数据的VO")
public class BusinessDataVO implements Serializable {

    @ApiModelProperty("新增用户数")
    private Integer newUsers;

    @ApiModelProperty("订单完成率")
    private Double orderCompletionRate;

    @ApiModelProperty("营业额")
    private BigDecimal turnover;

    @ApiModelProperty("平均客单价")
    private Double unitPrice;

    @ApiModelProperty("有效订单数")
    private Integer validOrderCount;
}
