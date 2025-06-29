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
@ApiModel("查询套餐总览接口的VO")
public class SetMealOverViewVO implements Serializable {

    @ApiModelProperty("查询套餐总览")
    private Integer discontinued;

    @ApiModelProperty("已起售套餐数量")
    private Integer sold;
}
