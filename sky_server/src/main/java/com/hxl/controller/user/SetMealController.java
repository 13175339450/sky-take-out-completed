package com.hxl.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxl.constant.RedisNameConstant;
import com.hxl.entity.SetMeal;
import com.hxl.result.Result;
import com.hxl.service.SetMealService;
import com.hxl.utils.RedisCacheUtil;
import com.hxl.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

@RestController("userSetMealController")
@RequestMapping("/user/setmeal")
@Slf4j
@Api(tags = "用户套餐相关的接口")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 根据分类id查询套餐
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    public Result<List<SetMeal>> querySetMealByCategoryId(Long categoryId) {
        log.info("根据分类id查询套餐: {}", categoryId);

//        String key = RedisNameConstant.SET_MEAL_CACHE + categoryId;
//
//        //查看redis里有没有缓存
//        String json = stringRedisTemplate.opsForValue().get(key);
//
//        //查到缓存
//        if (json != null){
//            //将json转换为实体对象
//            try {
//                vo = objectMapper.readValue(json, new TypeReference<List<SetMeal>>() {
//                });
//                log.info("从redis里读取套餐缓存:key为 {}", key);
//                //直接返回
//                return Result.success(vo);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//        }

        //查询起售中的套餐
        List<SetMeal> vo = null;
        //SpringCache
        vo = setMealService.querySetMealByCategoryId(categoryId);

        //加入redis
//        redisCacheUtil.addCache(key, vo);

        return Result.success(vo);
    }

    /**
     * 根据套餐id查询套餐包含的菜品
     */
    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品")
    public Result<List<DishItemVO>> queryDishBySetMealId(@PathVariable Long id) {
        log.info("根据套餐id查询包含的菜品: {}", id);

//        //查询redis
//        String key = RedisNameConstant.SET_MEAL_DISH_CACHE + id;
//        String json = stringRedisTemplate.opsForValue().get(key);
//        //redis里有缓存
//        if (json != null){
//            try {
//                vo = objectMapper.readValue(json, new TypeReference<List<DishItemVO>>() {
//                });
//                //redis里有缓存
//                log.info("从redis里读取套餐关联的菜品信息:key为 {}", key);
//                return Result.success(vo);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//        }

        //TODO: 可以加上停售的 (服务员替换)
        List<DishItemVO> vo = null;
        vo = setMealService.queryDishBySetMealId(id);

        //加入redis缓存
//        redisCacheUtil.addCache(key, vo);

        return Result.success(vo);
    }
}
