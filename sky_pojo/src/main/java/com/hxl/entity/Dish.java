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
@ApiModel("菜品表对应的实体类")
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜品id")
    private Long id;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("菜品价格")
    private BigDecimal price;

    @ApiModelProperty("菜品图片URL")
    private String image;

    @ApiModelProperty("菜品描述")
    private String description;

    @ApiModelProperty("菜品状态")
    private Integer status;

    @ApiModelProperty("菜品创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("菜品更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("菜品创建者id")
    private Long createUser;

    @ApiModelProperty("菜品更新者id")
    private Long updateUser;
}
