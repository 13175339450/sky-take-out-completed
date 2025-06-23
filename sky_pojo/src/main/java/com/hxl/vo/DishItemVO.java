package com.hxl.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.ResultSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("根据套餐id查询套餐包含菜品接口的VO")
public class DishItemVO implements Serializable {

    @ApiModelProperty("份数")
    private Integer copies;

    @ApiModelProperty("菜品描述")
    private String description;

    @ApiModelProperty("菜品图片URL")
    private String image;

    @ApiModelProperty("菜品名称")
    private String name;
}
