package com.hxl.controller.admin;

import com.hxl.constant.RedisNameConstant;
import com.hxl.dto.SetMealAddDTO;
import com.hxl.dto.SetMealEditDTO;
import com.hxl.dto.SetMealPageDTO;
import com.hxl.entity.SetMeal;
import com.hxl.result.PageResult;
import com.hxl.result.Result;
import com.hxl.service.SetMealService;
import com.hxl.utils.RedisCacheUtil;
import com.hxl.vo.SetMealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminSetMealController")
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags = "套餐管理相关接口")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    /**
     * 新增套餐
     */
    @PostMapping
    @ApiOperation("新增套餐")
    @CacheEvict(cacheNames = RedisNameConstant.SET_MEAL_CACHE, allEntries = true)
    public Result addSeaMeal(@RequestBody SetMealAddDTO setMealAddDTO) {
        log.info("新增套餐: {}", setMealAddDTO);

        setMealService.addSetMeal(setMealAddDTO);

        //清空套餐
        redisCacheUtil.flushCache(RedisNameConstant.SET_MEAL_CACHE);

        return Result.success();
    }

    /**
     * 分页查询套餐数据
     */
    @GetMapping("/page")
    @ApiOperation("分页查询套餐信息")
    public Result<PageResult> SetMealPage(SetMealPageDTO setMealPageDTO) {
        log.info("分页查询套餐信息: {}", setMealPageDTO);

        PageResult vo = setMealService.SetMealPage(setMealPageDTO);

        return Result.success(vo);
    }

    /**
     * 套餐起售、停售
     */
    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售、停售")
    @CacheEvict(cacheNames = RedisNameConstant.SET_MEAL_CACHE, allEntries = true)
    public Result startOrStopSetMeal(@PathVariable Integer status, Long id) {
        log.info("套餐起售、停售: {}, {}", status, id);

        setMealService.startOrStopSetMeal(status, id);

        //清空套餐
        redisCacheUtil.flushCache(RedisNameConstant.SET_MEAL_CACHE);

        return Result.success();
    }

    /**
     * 根据id查询套餐信息
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetMealVO> querySetMealById(@PathVariable Long id) {
        log.info("根据id查询套餐: {}", id);

        SetMealVO vo = setMealService.querySetMealById(id);

        return Result.success(vo);
    }

    /**
     * 修改套餐
     */
    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict(
            cacheNames = {RedisNameConstant.SET_MEAL_CACHE, RedisNameConstant.SET_MEAL_DISH_CACHE },
            allEntries = true
    )
    public Result editSetMeal(@RequestBody SetMealEditDTO setMealEditDTO) {
        log.info("修改套餐: {}", setMealEditDTO);

        setMealService.editSetMeal(setMealEditDTO);

//        //清空套餐缓存
//        redisCacheUtil.flushCache(RedisNameConstant.SET_MEAL_CACHE);
//        //清空套餐绑定的菜品缓存
//        redisCacheUtil.flushCache(RedisNameConstant.SET_MEAL_DISH_CACHE);

        return Result.success();
    }

    /**
     * 批量删除套餐
     */
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    @CacheEvict(
            cacheNames = {RedisNameConstant.SET_MEAL_CACHE, RedisNameConstant.SET_MEAL_DISH_CACHE },
            allEntries = true
    )
    public Result deleteSetMealBatch(@RequestParam List<Long> ids){
        log.info("批量删除套餐: {}", ids);

        setMealService.deleteSetMealBatch(ids);

//        //清空套餐缓存
//        redisCacheUtil.flushCache(RedisNameConstant.SET_MEAL_CACHE);
//        //清空套餐绑定的菜品缓存
//        redisCacheUtil.flushCache(RedisNameConstant.SET_MEAL_DISH_CACHE);

        return Result.success();
    }
}
