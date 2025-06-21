package com.hxl.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("菜品分页数据的VO")
public class DishPageVO implements Serializable {

    @ApiModelProperty("菜品id")
    private Long id;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("菜品价格")
    private Double price;

    @ApiModelProperty("菜品图片的URL")
    private String image;

    @ApiModelProperty("菜品描述")
    private String description;

    @ApiModelProperty("菜品状态")
    private Integer status;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("分类名称")
    private String categoryName;
}
