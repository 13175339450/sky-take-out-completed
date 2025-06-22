package com.hxl.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("套餐菜品表的实体类")
public class SetMealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("套餐和菜品关系id")
    private Long id;

    @ApiModelProperty("套餐id")
    private Long setmealId;

    @ApiModelProperty("菜品id")
    private Long dishId;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("菜品价格")
    private BigDecimal price;

    @ApiModelProperty("份数")
    private Integer copies;
}
