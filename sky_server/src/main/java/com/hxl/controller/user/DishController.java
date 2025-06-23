package com.hxl.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxl.constant.RedisNameConstant;
import com.hxl.result.Result;
import com.hxl.service.DishService;
import com.hxl.utils.RedisCacheUtil;
import com.hxl.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    /**
     * 根据categoryId查询菜品信息 (展示起售的菜品)
     *    左侧栏所对应的菜品
     */
    @GetMapping("list")
    @ApiOperation("根据categoryId查询菜品信息")
    public Result<List<DishVO>> queryDishByCategoryId(Long categoryId){
        log.info("根据categoryId查询菜品信息: {}", categoryId);

        String key = RedisNameConstant.DISH_CACHE + categoryId;
        //判断redis里有没有缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        List<DishVO> vo = null;
        //存在这个缓存key值
        if (json != null){
            //获取缓存
            try {
                vo = objectMapper.readValue(json, new TypeReference<List<DishVO>>() {
                });
                log.info("从redis里读取菜品缓存:key为 {}", key);
                //直接返回
                return Result.success(vo);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        vo = dishService.queryDishAndFlavorsByCategoryId(categoryId);
        //添加进redis
        redisCacheUtil.addCache(key, vo);

        return Result.success(vo);
    }
}
