package com.hxl.controller.admin;

import com.hxl.dto.OrderSearchDTO;
import com.hxl.entity.Orders;
import com.hxl.result.PageResult;
import com.hxl.result.Result;
import com.hxl.service.OrderService;
import com.hxl.vo.OrderStatisticsVO;
import com.hxl.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Slf4j
@Api(tags = "管理端订单相关接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单搜索 -- 分页查询
     */
    @GetMapping("/conditionSearch")
    @ApiOperation("订单搜索")
    public Result<PageResult> conditionSearch(OrderSearchDTO orderSearchDTO) {
        log.info("订单搜索: {}", orderSearchDTO);

        PageResult vo = orderService.conditionSearch(orderSearchDTO);

        return Result.success(vo);
    }

    /**
     * 各个状态的订单数量
     */
    @GetMapping("/statistics")
    @ApiOperation("各个状态的订单数量")
    public Result<OrderStatisticsVO> getAnyOrderStatus() {
        log.info("各个状态的订单数量");

        OrderStatisticsVO vo = orderService.getAnyOrderStatusNumber();

        return Result.success(vo);
    }

    /**
     * 查询订单详情
     */
    @GetMapping("/details/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> queryOrderDetail(@PathVariable Long id) {
        log.info("查询订单详情: {}", id);

        //调用user里封装过的方法
        OrderVO vo = orderService.queryOrderDetail(id);

        return Result.success(vo);
    }

    /**
     * 接单
     */
    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result confirmOrder(@RequestBody Orders orders) {
        log.info("接单: {}", orders.getId());

        orderService.confirmOrder(orders);

        return Result.success();
    }

    /**
     * 派送订单
     */
    @PutMapping("/delivery/{id}")
    @ApiOperation("派送订单")
    public Result deliveryOrder(@PathVariable Long id) {
        log.info("派送订单: {}", id);

        orderService.deliveryOrder(id);

        return Result.success();
    }

    /**
     * 完成订单
     */
    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result completedOrder(@PathVariable Long id) {
        log.info("完成订单");

        orderService.completedOrder(id);

        return Result.success();
    }

    /**
     * 取消订单
     */
    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result cancelOrder(@RequestBody Orders order) {
        log.info("取消订单: {}", order);

        orderService.cancelUserOrder(order);

        return Result.success();
    }

    /**
     * 拒单
     */
    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result rejectOrder(@RequestBody Orders order){
        log.info("拒单: {}", order);

        orderService.rejectOrder(order);

        return Result.success();
    }
}
