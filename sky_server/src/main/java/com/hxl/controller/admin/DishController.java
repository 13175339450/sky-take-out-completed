package com.hxl.controller.admin;

import com.hxl.constant.RedisNameConstant;
import com.hxl.dto.DishDTO;
import com.hxl.dto.DishPageDTO;
import com.hxl.entity.Dish;
import com.hxl.result.PageResult;
import com.hxl.result.Result;
import com.hxl.service.DishService;
import com.hxl.utils.RedisCacheUtil;
import com.hxl.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.License;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品管理相关接口")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisCacheUtil redisCacheUtil;


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

        //清空全部 因为原redis数据已不完整
        redisCacheUtil.flushCache(RedisNameConstant.DISH_CACHE);

        return Result.success();
    }

    /**
     * 新增菜品
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result addDish(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品: {}", dishDTO);

        dishService.addDish(dishDTO);

        //清空全部 因为原redis数据已不完整
        redisCacheUtil.flushCache(RedisNameConstant.DISH_CACHE);

        return Result.success();
    }

    /**
     * 根绝id查询菜品
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> queryDishById(@PathVariable Long id) {
        log.info("根据id查询菜品: {}", id);

        DishVO vo = dishService.queryDishById(id);

        return Result.success(vo);
    }

    /**
     * 修改菜品
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result updateDish(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品: {}", dishDTO);

        dishService.updateDish(dishDTO);

        //清空全部 因为原redis数据已不完整
        redisCacheUtil.flushCache(RedisNameConstant.DISH_CACHE);

        return Result.success();
    }

    /**
     * 根据分类id查询菜品
     *  TODO: 停售的不能被查询到
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> queryDishByCategoryId(Long categoryId) {
        log.info("根据分类id查询菜品信息: {}", categoryId);

        List<Dish> vo = dishService.queryDishByCategoryId(categoryId);

        return Result.success(vo);
    }

    /**
     * 批量删除菜品
     *    TODO: 接收 "1,2,3"这种参数时，需要加 @RequestParam注解
     *      mvc会自动将参数转换为List集合
     */
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result deleteDishBatch(@RequestParam List<Long> ids){
        log.info("批量删除菜品: {}", ids);

        dishService.deleteDishBatch(ids);

        //清空全部 因为原redis数据已不完整
        redisCacheUtil.flushCache(RedisNameConstant.DISH_CACHE);

        return Result.success();
    }
}
