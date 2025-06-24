package com.hxl.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxl.constant.RedisNameConstant;
import com.hxl.entity.Category;
import com.hxl.result.Result;
import com.hxl.service.CategoryService;
import com.hxl.utils.RedisCacheUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Slf4j
@Api(tags = "用户分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Autowired
    private ObjectMapper objectMapper;
    /**
     * 条件查询:
     *    查询小程序左侧分类栏
     *      TODO: 将分类内容缓存入redis里 当修改分类内容时(增删改时都要清空redis缓存)
     */
    @GetMapping("/list")
    @ApiOperation("条件查询")
    public Result<List<Category>> queryCategory(Integer type){
        log.info("用户端条件查询: {}", type);

        List<Category> vo = null;
//        //创建分类缓存的key
//        String key = RedisNameConstant.CATEGORY_CACHE + type;
//        //根据key去查询redis
//        String json = stringRedisTemplate.opsForValue().get(key);
//
//        if (json != null) {
//            try {
//                //手动反序列化
//                vo = objectMapper.readValue(json, new TypeReference<List<Category>>() {
//                });
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//        }
//
//        //redis里有缓存 直接返回
//        if (vo != null && !vo.isEmpty()){
//            log.info("读取redis缓存的左侧列表分类信息");
//            return Result.success(vo);
//        }

        //没有缓存去sql里查询 并且存入redis
        //不显示停售的分类 去sql里查询
        //TODO 在方法内部用SpringCache缓存
        vo = categoryService.queryCategoryByType(type);
//        //存入redis
////        redisTemplate.opsForValue().set(key, vo);
//        redisCacheUtil.addCache(key, vo);

        return Result.success(vo);
    }
}
