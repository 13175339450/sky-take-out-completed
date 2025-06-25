package com.hxl.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class OrderSearchDTO implements Serializable {

    @ApiModelProperty("开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("订单号")
    private String number;

    @ApiModelProperty("当前页")
    private Integer page;

    @ApiModelProperty("页容量")
    private Integer pageSize;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("订单状态")
    private Integer status;
}
