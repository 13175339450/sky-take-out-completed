package com.hxl.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hxl.constant.MessageConstant;
import com.hxl.constant.StatusConstant;
import com.hxl.context.BaseContext;
import com.hxl.dto.HistoryOrdersDTO;
import com.hxl.dto.OrderPaymentDTO;
import com.hxl.dto.OrderSearchDTO;
import com.hxl.dto.OrderSubmitDTO;
import com.hxl.entity.*;
import com.hxl.exception.OrderSubmitFailException;
import com.hxl.mapper.*;
import com.hxl.result.PageResult;
import com.hxl.service.OrderService;
import com.hxl.vo.OrderPaymentVO;
import com.hxl.vo.OrderSubmitVO;
import com.hxl.vo.OrderVO;
import com.hxl.webSocket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    //声明全局变量 用于跳过微信支付
    private Orders orders;

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 提交当前用户的订单
     */
    @Override
    @Transactional //开启事务
    public OrderSubmitVO submitOrder(OrderSubmitDTO orderSubmitDTO) {
        //校验用户提交的数据是否合法 地址不能为空，购物车不能为空 前端传过来的地址簿id一定不为空
        AddressBook condition = AddressBook.builder().id(orderSubmitDTO.getAddressBookId()).build();
        AddressBook addressBook= addressBookMapper.querySingleAddress(condition);
        if (addressBook == null){
            //地址为空
            throw new OrderSubmitFailException(MessageConstant.ADDRESS_IS_NULL);
        }

        //查询购物车的信息 根据userId
        //获取userId
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.queryShoppingCartByUserId(userId);

        if (shoppingCarts == null || shoppingCarts.isEmpty()){
            //购物车不能为空
            throw new OrderSubmitFailException(MessageConstant.SHOPPING_CAR_IS_NULL);
        }

        //可以提交数据 并且清空购物车
        Orders orders = new Orders();
        BeanUtils.copyProperties(orderSubmitDTO, orders);
        //获取当前user的其它信息
        User user = userMapper.queryUserById(userId);
        //设置用户id、用户名、下单时间、电话(地址簿里的电话)、支付方式、订单状态、订单号、收货人
        orders.setUserId(userId);
        orders.setUserName(user.getName());
        orders.setOrderTime(LocalDateTime.now());
        orders.setPhone(addressBook.getPhone());//地址里的电话
        orders.setPayMethod(StatusConstant.PAY_WECHAT);//微信支付 前端已传入(可以不设置)
        orders.setStatus(Orders.PENDING_PAYMENT);//待付款
        orders.setPayStatus(Orders.UN_PAID);//未支付状态
        orders.setNumber(String.valueOf(System.currentTimeMillis()));//订单号
        //设置完整的地址
        orders.setAddress(addressBook.getProvinceName() + addressBook.getCityName() + addressBook.getDistrictName() + addressBook.getDetail());
        orders.setConsignee(addressBook.getConsignee());//地址里的收货人
        //插入一条订单数据
        orderMapper.insertOrder(orders);

        //获取回显的id值
        Long orderId = orders.getId();

        //TODO：给跳过微信支付所需要的orderId赋值
        this.orders = orders;

        //订单封装集合
        List<OrderDetail> orderDetailList = new ArrayList<>();
        //向订单明细表里插入多条数据 批量插入
        for (ShoppingCart shoppingCart : shoppingCarts) {
            //赋值orderId
            OrderDetail orderDetail = OrderDetail.builder().orderId(orderId).build();
            BeanUtils.copyProperties(shoppingCart, orderDetail);
            //添加进行封装集合
            orderDetailList.add(orderDetail);
        }
        //批量插入
        orderDetailMapper.insertOrderDetailBatch(orderDetailList);

        //清空当前用户的购物车
        shoppingCartMapper.cleanShoppingCart(userId);

        return OrderSubmitVO.builder()
                .id(orderId)
                .orderAmount(orders.getAmount())
                .orderNumber(orders.getNumber())
                .orderTime(orders.getOrderTime()).build();
    }

    /**
     * 订单支付
     */
    @Override
    public OrderPaymentVO orderPayment(OrderPaymentDTO orderPaymentDTO) {
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.queryUserById(userId);

        //调用微信支付接口，生成预支付交易单
//        JSONObject jsonObject = weChatPayUtil.pay(
//                ordersPaymentDTO.getOrderNumber(), //商户订单号
//                new BigDecimal(0.01), //支付金额，单位 元
//                "苍穹外卖订单", //商品描述
//                user.getOpenid() //微信用户的openid
//        );
//
//        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
//            throw new OrderBusinessException("该订单已支付");
//        }

        //改写的微信支付
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "ORDERPAID");
        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));
        Integer OrderPaidStatus = Orders.PAID;//支付状态，已支付
        Integer OrderStatus = Orders.TO_BE_CONFIRMED;  //订单状态，待接单
        LocalDateTime check_out_time = LocalDateTime.now();//更新支付时间
        orderMapper.updateStatus(OrderStatus, OrderPaidStatus, check_out_time, this.orders.getId());
        return vo;
    }

    /**
     * 支付成功
     */
    @Override
    public void paySuccess(String outTradeNo) {

        // 根据订单号查询订单
        Orders ordersDB = orderMapper.getByNumber(outTradeNo);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
//                .packAmount(ordersDB.getPackAmount()) //TODO: 修复bug 因为packAmount为int类型 不赋值的话 默认为0 而 0 != null 所以会更新为0
//                .tablewareNumber(ordersDB.getTablewareNumber())//TODO: 修复bug 同上
                .build();

        orderMapper.update(orders);

        //进行相应
        //1.相应内容封装 type、orderId、订单号
        Map map = new HashMap();
        map.put("type", 1);//1表示来单提醒 2表示客户催单
        map.put("orderId", this.orders.getId());
        map.put("content", "订单号：" + this.orders.getNumber());
        //2.转换为JSON格式
        String json = JSON.toJSONString(map);
        //3.发送给服务端
        //在单个 TCP 连接上进行全双工通信的协议，能在客户端和服务器之间建立实时连接
        webSocketServer.sendToAllClient(json);
    }

    /**
     * 查询订单的详情信息
     */
    @Override
    public OrderVO queryOrderDetail(Long id) {
        OrderVO orderVO = new OrderVO();

        //查询订单的基本信息
        Orders orders = orderMapper.queryOrderById(id);

        //查询订单的详细信息
        List<OrderDetail> orderDetailList = orderDetailMapper.queryOrderDetailByOrderId(id);

        //属性拷贝
        BeanUtils.copyProperties(orders, orderVO);
        //TODO: bug修复 属性用赋值 不能进行属性拷贝!!!
        orderVO.setOrderDetailList(orderDetailList);

        return orderVO;
    }

    /**
     * 历史订单查询
     */
    @Override
    public PageResult queryHistoryOrders(HistoryOrdersDTO historyOrdersDTO) {
        //设置分页数据
        PageHelper.startPage(historyOrdersDTO.getPage(), historyOrdersDTO.getPageSize());

        //分页查询 基本信息
        Orders condition = Orders.builder().status(historyOrdersDTO.getStatus()).userId(BaseContext.getCurrentId()).build();
        Page<Orders> page = orderMapper.historyOrdersPage(condition);

        //获取基本信息
        long total = page.getTotal();
        List<Orders> orders = page.getResult();

        //对每一个历史订单进行单独填充订单细节的内容
        List<OrderVO> records = new ArrayList<>();
        if (orders != null && !orders.isEmpty()){
            for (Orders o : orders) {
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(o, orderVO);
                //根据订单id查询内容
                List<OrderDetail> list = orderDetailMapper.queryOrderDetailByOrderId(o.getId());
                orderVO.setOrderDetailList(list);
                //添加结果
                records.add(orderVO);
            }
        }

        return new PageResult(total, records);
    }

    /**
     * 再来一单
     */
    @Override
    public void repetitionOrder(Long id) {
        //把上一次的单的 订单明细 再重新加入购物车 根据订单id
        List<OrderDetail> orderDetailList = orderDetailMapper.queryOrderDetailByOrderId(id);

        //将这些内容重新插入购物车表里 但要自己填充 userId、createTime
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            //填充 userId、createTime
            ShoppingCart shoppingCart = ShoppingCart.builder().userId(BaseContext.getCurrentId()).createTime(LocalDateTime.now()).build();
            //属性拷贝
            BeanUtils.copyProperties(orderDetail, shoppingCart);
            shoppingCarts.add(shoppingCart);
        }
        //批量插入
        shoppingCartMapper.insertShoppingCartBatch(shoppingCarts);
    }

    /**
     * 取消订单
     */
    @Override
    public void cancelOrder(Long id) {
        Orders order = Orders.builder()
                .id(id)//订单id
                .status(Orders.CANCELLED)//已取消
                .payStatus(Orders.REFUND)//已退款
                .cancelReason(MessageConstant.USER_CANCEL)//取消原因
                .cancelTime(LocalDateTime.now()).build();

        orderMapper.update(order);
    }

    /**
     * 订单搜索
     */
    @Override
    public PageResult conditionSearch(OrderSearchDTO orderSearchDTO) {
        //设置分页
        PageHelper.startPage(orderSearchDTO.getPage(), orderSearchDTO.getPageSize());

        //分页查询
        Page<OrderVO> page = orderMapper.conditionSearch(orderSearchDTO);

        //获取结果
        long total = page.getTotal();
        List<OrderVO> records = page.getResult();

        //为每个订单构建 简要的菜品数据描述 orderDishes的值
        for (OrderVO order : records) {
            //根据订单id去查找 订单明细数据
            List<OrderDetail> orderDetailList = orderDetailMapper.queryOrderDetailByOrderId(order.getId());
            //组合成字符串
            String orderDishes = "";
            for (OrderDetail orderDetail : orderDetailList) {
                orderDishes += orderDetail.getName() + " * " + orderDetail.getAmount() + " ;";
            }
            order.setOrderDishes(orderDishes);
        }

        return new PageResult(total, records);
    }
}
