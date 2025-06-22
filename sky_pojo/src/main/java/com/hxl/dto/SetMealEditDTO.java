package com.hxl.dto;

import com.hxl.entity.SetMealDish;
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
@ApiModel("修改套餐接口的DTO")
public class SetMealEditDTO implements Serializable {

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("套餐描述")
    private String description;

    @ApiModelProperty("套餐id")
    private Long id;

    @ApiModelProperty("套餐图片URL")
    private String image;

    @ApiModelProperty("套餐名称")
    private String name;

    @ApiModelProperty("套餐价格")
    private BigDecimal price;

    @ApiModelProperty("套餐起售状态")
    private Integer status;

    @ApiModelProperty("套餐和菜品的关联关系")
    private List<SetMealDish> setmealDishes;
}
