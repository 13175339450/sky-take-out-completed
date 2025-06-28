package com.hxl.vo;

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
@ApiModel("营业额统计接口的VO")
public class TurnoverReportVO implements Serializable {

    //日期之间以逗号分隔
    @ApiModelProperty("日期列表")
    private String dateList;

    //营业额之间以逗号分隔
    @ApiModelProperty("营业额列表")
    private String turnoverList;
}
