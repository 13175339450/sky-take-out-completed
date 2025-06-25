package com.hxl.controller.admin;

import com.hxl.dto.OrderSearchDTO;
import com.hxl.result.PageResult;
import com.hxl.result.Result;
import com.hxl.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
