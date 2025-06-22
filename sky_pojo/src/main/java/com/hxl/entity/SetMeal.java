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
@ApiModel("套餐表对应的实体类")
public class SetMeal implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("套餐id")
    private Long id;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("套餐名称")
    private String name;

    @ApiModelProperty("套餐价格")
    private BigDecimal price;

    @ApiModelProperty("套餐状态")
    private Integer status;

    @ApiModelProperty("套餐描述")
    private String description;

    @ApiModelProperty("套餐图片的URL")
    private String image;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建者id")
    private Long createUser;

    @ApiModelProperty("更新者id")
    private Long updateUser;
}
