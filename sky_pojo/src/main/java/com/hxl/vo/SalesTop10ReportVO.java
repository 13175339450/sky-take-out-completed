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
@ApiModel("查询销量排名top10的接口的VO")
public class SalesTop10ReportVO implements Serializable {

    @ApiModelProperty("商品名称列表")
    private String nameList;

    @ApiModelProperty("销量列表")
    private String numberList;
}
