package com.hxl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("用户下单接口的DTO")
public class OrderSubmitDTO implements Serializable {

    @ApiModelProperty("地址簿id")
    private Long addressBookId;

    @ApiModelProperty("总金额")
    private BigDecimal amount;

    @ApiModelProperty("配送状态")
    private Integer deliveryStatus;

    @ApiModelProperty("预计送达时间")
    /* TODO: 前端传递的日期格式为:yyyy-MM-dd HH:mm:ss 而LocalDateTime的格式为 yyyy-MM-dd'T'HH:mm:ss.SSS（如 2025-06-25T17:03:00）
            此时需要进行格式转换： 使用了 Jackson 库的@JsonFormat注解，
                               用于指定 Java 对象属性在序列化为 JSON 或从 JSON 反序列化为 Java 对象时的格式。
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;

    @ApiModelProperty("打包费")
    private Integer packAmount;

    @ApiModelProperty("付款方式")
    private Integer payMethod;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("餐具数量")
    private Integer tablewareNumber;

    @ApiModelProperty("餐具数量状态")
    private Integer tablewareStatus;
}
