package com.hxl.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("员工分页接口的DTO")
public class EmployeePageDTO implements Serializable {

    @ApiModelProperty("员工姓名")
    private String name;

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("页容量")
    private Integer pageSize;
}
