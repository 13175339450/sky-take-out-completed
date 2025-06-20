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
@ApiModel("分类分页查询接口的DTO")
public class CategoryPageDTO implements Serializable {

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("页容量")
    private Integer pageSize;

    @ApiModelProperty("分类类型")
    private Integer type;
}
