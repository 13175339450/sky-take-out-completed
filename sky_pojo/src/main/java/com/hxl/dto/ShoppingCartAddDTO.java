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
@ApiModel("添加购物车接口的DTO")
public class ShoppingCartAddDTO implements Serializable {

    @ApiModelProperty("口味")
    private String dishFlavor;

    @ApiModelProperty("菜品id")
    private Long dishId;

    @ApiModelProperty("套餐id")
    private Long setmealId;


    //为后面的查询所额外添加的
    @ApiModelProperty("用户id")
    private Long userId;
}
