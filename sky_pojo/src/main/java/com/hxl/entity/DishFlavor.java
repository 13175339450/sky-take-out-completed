package com.hxl.entity;

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
@ApiModel("菜品口味表对应的实体类")
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("口味id")
    private Long id;

    @ApiModelProperty("菜品id")
    private Long dishId;

    @ApiModelProperty("口味种类名称")
    private String name;

    @ApiModelProperty("口味详情信息")
    private String value;

}
