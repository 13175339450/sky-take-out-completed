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
@ApiModel("菜品分页查询接口的DTO")
public class DishPageDTO implements Serializable {

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("页容量")
    private Integer pageSize;

    @ApiModelProperty("分类状态")
    private Integer status;
}
