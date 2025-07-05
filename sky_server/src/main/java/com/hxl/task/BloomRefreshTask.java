package com.hxl.task;


import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.hxl.constant.RedisNameConstant;
import com.hxl.entity.Category;
import com.hxl.entity.Dish;
import com.hxl.entity.SetMeal;
import com.hxl.mapper.CategoryMapper;
import com.hxl.mapper.DishMapper;
import com.hxl.mapper.SetMealMapper;
import com.hxl.properties.BloomProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Slf4j
public class BloomRefreshTask {

    // 使用AtomicReference保证线程安全
    private final AtomicReference<BloomFilter<String>> bloomFilterRef = new AtomicReference<>();

    @Autowired
    private BloomFilter<String> bloomFilter;

    @Autowired
    private BloomProperties bloomProperties;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetMealMapper setMealMapper;

    @Scheduled(cron = "0 0 1 * * ?")
    public void bloomRefresh() {
        log.info("定时任务开始刷新布隆过滤器...");
        refreshBloomFilter();
    }

    /**
     * 刷新布隆过滤器（核心方法）
     */
    private void refreshBloomFilter() {
        // 创建新的布隆过滤器
        BloomFilter<String> newFilter = BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()),
                bloomProperties.getExpectedInsertions(),
                bloomProperties.getFpp()
        );

        // 加载分类数据
        List<Category> categoryList = categoryMapper.queryCategory(null);
        newFilter.put(RedisNameConstant.CATEGORY_CACHE + "::all");
        for (Category category : categoryList) {
            newFilter.put(RedisNameConstant.CATEGORY_CACHE + "::" + category.getType());
        }

        // 加载菜品数据
        List<Dish> dishes = dishMapper.queryDish(new Dish());
        for (Dish dish : dishes) {
            newFilter.put(RedisNameConstant.DISH_CACHE + "::" + dish.getCategoryId());
        }

        // 加载套餐数据
        List<SetMeal> setMeals = setMealMapper.querySetMeal(new SetMeal());
        for (SetMeal setMeal : setMeals) {
            newFilter.put(RedisNameConstant.SET_MEAL_CACHE + "::" + setMeal.getCategoryId());
        }

        // 原子替换旧过滤器
        bloomFilterRef.set(newFilter);
        log.info("布隆过滤器刷新完成，新过滤器已生效");
    }
}
