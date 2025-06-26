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
@ApiModel("查询各个订单状态接口的VO")
public class OrderStatisticsVO implements Serializable {

    @ApiModelProperty("带派送数量")
    private Integer confirmed;

    @ApiModelProperty("派送中数量")
    private Integer deliveryInProgress;

    @ApiModelProperty("待接单数量")
    private Integer toBeConfirmed;
}

