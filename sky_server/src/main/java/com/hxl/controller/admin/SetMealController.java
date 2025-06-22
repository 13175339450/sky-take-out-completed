package com.hxl.controller.admin;

import com.hxl.dto.SetMealAddDTO;
import com.hxl.result.Result;
import com.hxl.service.SetMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
