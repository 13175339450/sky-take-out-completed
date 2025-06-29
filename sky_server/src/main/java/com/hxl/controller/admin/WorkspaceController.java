package com.hxl.controller.admin;

import com.hxl.result.Result;
import com.hxl.service.WorkspaceService;
import com.hxl.vo.BusinessDataVO;
import com.hxl.vo.DishOverViewVO;
import com.hxl.vo.OrderOverViewVO;
import com.hxl.vo.SetMealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminWorkspaceController")
@RequestMapping("/admin/workspace")
@Slf4j
@Api(tags = "工作台接口")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    /**
     * 查询今日运营数据
     */
    @GetMapping("/businessData")
    @ApiOperation("查询今日运营数据")
    public Result<BusinessDataVO> businessDate() {
        log.info("查询今日运营数据");

        BusinessDataVO vo = workspaceService.businessData();

        return Result.success(vo);
    }

    /**
     * 查询订单管理数据
     */
    @GetMapping("/overviewOrders")
    @ApiOperation("查询订单管理数据")
    public Result<OrderOverViewVO> overviewOrders() {
        log.info("查询订单管理数据");

        OrderOverViewVO vo = workspaceService.overviewOrders();

        return Result.success(vo);
    }

    /**
     * 查询菜品总览
     */
    @GetMapping("/overviewDishes")
    @ApiOperation("查询菜品总览")
    public Result<DishOverViewVO> overviewDishes() {
        log.info("查询菜品总览");

        DishOverViewVO vo = workspaceService.overviewDishes();

        return Result.success(vo);
    }

    /**
     * 查询套餐总览
     */
    @GetMapping("/overviewSetmeals")
    @ApiOperation("查询套餐总览")
    public Result<SetMealOverViewVO> overviewSetMeals(){
        log.info("查询套餐总览");

        SetMealOverViewVO vo = workspaceService.overviewSetMeals();

        return Result.success(vo);
    }
}
