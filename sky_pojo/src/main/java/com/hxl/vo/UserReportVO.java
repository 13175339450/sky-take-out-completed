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
@ApiModel("用户统计接口的VO")
public class UserReportVO implements Serializable {

    @ApiModelProperty("日期列表")
    private String dateList;

    @ApiModelProperty("新增用户列表")
    private String newUserList;

    @ApiModelProperty("总用户列表")
    private String totalUserList;
}
