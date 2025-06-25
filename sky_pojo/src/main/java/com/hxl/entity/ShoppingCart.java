package com.hxl.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("购物车表对应的实体类")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("金额")
    private BigDecimal amount;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("菜品口味")
    private String dishFlavor;

    @ApiModelProperty("菜品id")
    private Long dishId;

    @ApiModelProperty("商品名")
    private String name;

    @ApiModelProperty("商品URL")
    private String image;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("商品数量")
    private Integer number;

    @ApiModelProperty("套餐id")
    private Long setmealId;

    @ApiModelProperty("用户id")
    private Long userId;
}
