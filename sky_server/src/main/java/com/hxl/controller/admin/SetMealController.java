package com.hxl.controller.admin;

import com.hxl.dto.SetMealAddDTO;
import com.hxl.dto.SetMealPageDTO;
import com.hxl.result.PageResult;
import com.hxl.result.Result;
import com.hxl.service.SetMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminSetMealController")
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐管理相关接口")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    /**
     * 新增套餐
     */
    @PostMapping
    @ApiOperation("新增套餐")
    public Result addSeaMeal(@RequestBody SetMealAddDTO setMealAddDTO){
        log.info("新增套餐: {}", setMealAddDTO);

        setMealService.addSetMeal(setMealAddDTO);

        return Result.success();
    }

    /**
     * 分页查询套餐数据
     */
    @GetMapping("/page")
    @ApiOperation("分页查询套餐信息")
    public Result<PageResult> SetMealPage(SetMealPageDTO setMealPageDTO){
        log.info("分页查询套餐信息: {}", setMealPageDTO);

        PageResult vo = setMealService.SetMealPage(setMealPageDTO);

        return Result.success(vo);
    }
}
