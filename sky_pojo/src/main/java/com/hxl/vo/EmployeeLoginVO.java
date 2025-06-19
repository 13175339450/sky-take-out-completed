package com.hxl.vo;

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
@ApiModel(description = "员工登录返回的数据格式")
public class EmployeeLoginVO {

    @ApiModelProperty("主键值")
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("jwt令牌")
    String token;

    @ApiModelProperty("用户名")
    String userName;
}
