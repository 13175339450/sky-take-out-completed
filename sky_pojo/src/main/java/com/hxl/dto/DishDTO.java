package com.hxl.dto;

import com.hxl.entity.DishFlavor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("新增菜品接口的DTO")
public class DishDTO implements Serializable {

    @ApiModelProperty("分类id")
    public Long categoryId;

    @ApiModelProperty("菜品描述")
    private String description;

    @ApiModelProperty("菜品id")
    private Long id;

    @ApiModelProperty("菜品图片的URL")
    private String image;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("菜品价格")
    private BigDecimal price;

    @ApiModelProperty("菜品状态")
    private Integer status;

    @ApiModelProperty("口味列表")
    List<DishFlavor> flavors;
}
