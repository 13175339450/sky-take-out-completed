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
@ApiModel("修改后的账户密码的DTO")
public class EditPasswordDTO implements Serializable {

    @ApiModelProperty("员工id")
    private Long empId;

    @ApiModelProperty("新密码")
    private String newPassword;

    @ApiModelProperty("旧密码")
    private String oldPassword;
}
