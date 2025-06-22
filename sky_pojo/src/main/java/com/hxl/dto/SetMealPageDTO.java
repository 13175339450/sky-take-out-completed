package com.hxl.dto;

import io.swagger.annotations.Api;
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
@ApiModel("套餐分页查询的DTO")
public class SetMealPageDTO implements Serializable {

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("套餐名称")
    private String name;

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("页容量")
    private Integer pageSize;

    @ApiModelProperty("套餐起售状态")
    private Integer status;
}
