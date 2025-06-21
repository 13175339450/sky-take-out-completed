package com.hxl.vo;

import com.hxl.entity.DishFlavor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("根据id查询菜品接口的VO")
public class DishVO implements Serializable {

    @ApiModelProperty("菜品id")
    private Long id;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("分类id")
    private Long categoryId;

    @ApiModelProperty("分类名称")
    private String categoryName;

    @ApiModelProperty("菜品价格")
    private BigDecimal price;

    @ApiModelProperty("菜品图片URL")
    private String image;

    @ApiModelProperty("菜品描述")
    private String description;

    @ApiModelProperty("菜品状态")
    private Integer status;

    @ApiModelProperty("菜品更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("口味信息")
    private List<DishFlavor> flavors;
}
