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
@ApiModel("查询菜品总览接口的VO")
public class DishOverViewVO implements Serializable {

    @ApiModelProperty("已停售菜品数量")
    private Integer discontinued;

    @ApiModelProperty("已起售菜品数量")
    private Integer sold;
}
