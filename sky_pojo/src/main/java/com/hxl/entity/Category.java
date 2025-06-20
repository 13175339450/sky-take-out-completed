package com.hxl.entity;

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
@ApiModel("分类表的实体类对象")
public class Category implements Serializable {
    @ApiModelProperty("分类id")
    private Long id;

    @ApiModelProperty("分类类型")
    private Integer type;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类排序")
    private Integer sort;

    @ApiModelProperty("分类状态")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建者id")
    private Long createUser;

    @ApiModelProperty("更新者id")
    private Long updateUser;
}
