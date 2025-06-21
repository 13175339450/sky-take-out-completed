package com.hxl.controller.admin;

import com.hxl.dto.DishAddDTO;
import com.hxl.dto.DishPageDTO;
import com.hxl.result.PageResult;
import com.hxl.result.Result;
import com.hxl.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品管理相关接口")
public class DishController {

    @Autowired
    private DishService dishService;


    /**
     * 菜品分页查询
     */
    @GetMapping("page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> dishPage(DishPageDTO dishPageDTO) {
        log.info("菜品分页查询: {}", dishPageDTO);

        PageResult vo = dishService.dishPage(dishPageDTO);

        return Result.success(vo);
    }

    /**
     * 菜品的启售、停售
     */
    @PostMapping("/status/{status}")
    @ApiOperation("菜品启售、停售")
    public Result startOrStopDish(@PathVariable Integer status, Long id) {
        log.info("菜品启售、停售: {}, {}", status, id);

        dishService.startOrStopDish(status, id);

        return Result.success();
    }

    /**
     * 新增菜品
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result addDish(@RequestBody DishAddDTO dishAddDTO) {
        log.info("新增菜品: {}", dishAddDTO);

        dishService.addDish(dishAddDTO);

        return Result.success();
    }
}
