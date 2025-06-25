package com.hxl.vo;

import com.hxl.entity.OrderDetail;
import com.hxl.entity.Orders;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

//TODO： 继承父类的set/get方法 间接继承了父类的成员属性
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("查询订单详情接口的VO")
public class OrderVO extends Orders implements Serializable {

    //在后续显示菜品信息时用
    //订单菜品信息 菜品名 + 份数 + ;
    @ApiModelProperty("订单菜品信息")
    private String orderDishes;

    //订单详情
    @ApiModelProperty("订单细节信息")
    private List<OrderDetail> orderDetailList;

}
