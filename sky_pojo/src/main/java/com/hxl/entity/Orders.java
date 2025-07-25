package com.hxl.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ApiModel("订单表对应的实体类")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
     */
    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer TO_BE_CONFIRMED = 2;
    public static final Integer CONFIRMED = 3;
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;
    /**
     * 支付状态 0未支付 1已支付 2退款
     */
    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    /**
     * 配送状态 1.立即送出 0选择时间
     */
    public static final Integer DELIVERY = 1;
    public static final Integer NOT_DELIVERY = 0;

    //支付方式 1微信，2支付宝
    @ApiModelProperty("支付方式")
    private Integer payMethod;

    //订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
    @ApiModelProperty("订单状态")
    private Integer status;

    @ApiModelProperty("订单id")
    private Long id;

    //订单号
    @ApiModelProperty("订单号")
    private String number;

    //下单用户id
    @ApiModelProperty("下单用户id")
    private Long userId;

    //地址簿id
    @ApiModelProperty("地址簿id")
    private Long addressBookId;

    //下单时间
    @ApiModelProperty("下单时间")
    private LocalDateTime orderTime;

    //结账时间
    @ApiModelProperty("结账时间")
    private LocalDateTime checkoutTime;

    //支付状态 0未支付 1已支付 2退款
    @ApiModelProperty("支付状态")
    private Integer payStatus;

    //实收金额
    @ApiModelProperty("实收金额")
    private BigDecimal amount;

    //备注
    @ApiModelProperty("备注")
    private String remark;

    //用户名
    @ApiModelProperty("用户名")
    private String userName;

    //手机号
    @ApiModelProperty("手机号")
    private String phone;

    //地址
    @ApiModelProperty("地址")
    private String address;

    //收货人
    @ApiModelProperty("收货人")
    private String consignee;

    //订单取消原因
    @ApiModelProperty("订单取消原因")
    private String cancelReason;

    //订单拒绝原因
    @ApiModelProperty("订单拒绝原因")
    private String rejectionReason;

    //订单取消时间
    @ApiModelProperty("订单取消时间")
    private LocalDateTime cancelTime;

    //预计送达时间
    @ApiModelProperty("预计送达时间")
    private LocalDateTime estimatedDeliveryTime;

    //配送状态  1立即送出  0选择具体时间
    @ApiModelProperty("配送状态")
    private Integer deliveryStatus;

    //送达时间
    @ApiModelProperty("送达时间")
    private LocalDateTime deliveryTime;

    //打包费
    @ApiModelProperty("打包费")
    private int packAmount;

    //餐具数量
    @ApiModelProperty("餐具数量")
    private int tablewareNumber;

    //餐具数量状态  1按餐量提供  0选择具体数量
    @ApiModelProperty("餐具数量状态")
    private Integer tablewareStatus;

}
