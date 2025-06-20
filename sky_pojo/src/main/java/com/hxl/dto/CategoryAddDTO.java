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
@ApiModel("新增分类的DTO")
public class CategoryAddDTO implements Serializable {

    @ApiModelProperty("分类id")
    private Long id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("分类类型")
    private Integer type;
}
