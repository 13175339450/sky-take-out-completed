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
@ApiModel("历史订单查询接口的DTO")
public class HistoryOrdersDTO implements Serializable {

    @ApiModelProperty("页面")
    private Integer page;

    @ApiModelProperty("页容量")
    private Integer pageSize;

    @ApiModelProperty("订单状态")
    private Integer status;
}
