package com.hxl.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("用户下单接口的VO")
public class OrderSubmitVO implements Serializable {

    @ApiModelProperty("订单id")
    private Long id;

    @ApiModelProperty("订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("订单号")
    private String orderNumber;

    @ApiModelProperty("下单时间")
    private LocalDateTime orderTime;
}
