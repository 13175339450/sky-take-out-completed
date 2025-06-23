package com.hxl.controller.user;

import com.hxl.result.Result;
import com.hxl.service.DishService;
import com.hxl.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "用户菜品的相关接口")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 根据categoryId查询菜品信息 (展示起售的菜品)
     *    左侧栏所对应的菜品
     */
    @GetMapping("list")
    @ApiOperation("根据categoryId查询菜品信息")
    public Result<List<DishVO>> queryDishByCategoryId(Long categoryId){
        log.info("根据categoryId查询菜品信息: {}", categoryId);

        List<DishVO> vo = dishService.queryDishAndFlavorsByCategoryId(categoryId);

        return Result.success(vo);
    }
}
