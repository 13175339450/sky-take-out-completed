package com.hxl.dto;

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
@ApiModel("订单支付接口的DTO")
public class OrderPaymentDTO implements Serializable {

    @ApiModelProperty("订单号")
    private String orderNumber;

    @ApiModelProperty("支付方式")
    private Integer payMethod;
}
