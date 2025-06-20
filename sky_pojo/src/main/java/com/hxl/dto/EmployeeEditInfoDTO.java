package com.hxl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("编辑员工接口的DTO")
public class EmployeeEditInfoDTO {

    @ApiModelProperty("员工id")
    private Long id;

    @ApiModelProperty("身份证")
    private String idNumber;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("用户名")
    private String username;
}
