package com.hxl.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("销量排名前十的商品")
public class SalesTop10 implements Serializable {

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("销量")
    private Integer number;
}
