package com.hxl.entity;

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
@ApiModel("地址簿表对应的相关接口")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("地址簿id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("省级区划编号")
    private String provinceCode;

    @ApiModelProperty("省份名称")
    private String provinceName;

    @ApiModelProperty("市级区划编号")
    private String cityCode;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("区级区划编号")
    private String districtCode;

    @ApiModelProperty("地区名称")
    private String districtName;

    @ApiModelProperty("详细地址")
    private String detail;

    @ApiModelProperty("标签")
    private String label;

    @ApiModelProperty("是否为默认地址")
    private Integer isDefault;
}
