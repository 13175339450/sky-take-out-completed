package com.hxl.vo;

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
@ApiModel("用户登录接口的VO")
public class UserLoginVO implements Serializable {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("微信用户id")
    private String openid;

    @ApiModelProperty("jwt令牌")
    private String token;
}
