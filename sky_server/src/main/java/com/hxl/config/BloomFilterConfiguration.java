package com.hxl.config;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.hxl.constant.MessageConstant;
import com.hxl.constant.RedisNameConstant;
import com.hxl.constant.StatusConstant;
import com.hxl.entity.Category;
import com.hxl.entity.Dish;
import com.hxl.entity.SetMeal;
import com.hxl.mapper.CategoryMapper;
import com.hxl.mapper.DishMapper;
import com.hxl.mapper.SetMealMapper;
import com.hxl.properties.BloomProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.List;

/**
 * BloomFilter配置类，负责创建和初始化布隆过滤器实例
 * 布隆过滤器是一种空间效率极高的概率型数据结构，用于判断一个元素是否存在于集合中
 */
@Configuration
@Slf4j
public class BloomFilterConfiguration {

    /**
     * 注入布隆过滤器配置属性，从application.properties或application.yml加载配置
     */
    @Autowired
    private BloomProperties bloomProperties;

    @Autowired
    private BloomFilter<String> bloomFilter;//创建布隆过滤器的实例

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetMealMapper setMealMapper;

    /**
     * 创建并配置布隆过滤器实例
     *
     * @return 字符串类型的布隆过滤器实例
     */
    @Bean
    public BloomFilter<String> bloomFilter() {
        /* 创建布隆过滤器实例
         * Funnels.stringFunnel(Charset.defaultCharset()) 指定过滤的对象为字符串类型 使用系统默认字符集
         * bloomProperties.getExpectedInsertions() 设置期望插入的元素数量
         * bloomProperties.getFpp() 设置允许的误判率(False Positive Probability) */
        return BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()),
                bloomProperties.getExpectedInsertions(),
                bloomProperties.getFpp()
        );
    }

    /**
     * 初始化Bloom过滤器：
     *  TODO：预热！每次启动项目时将数据库中的有效数据加载到布隆过滤器中
     */
    @PostConstruct
    public void initBloomFilter() {
        log.info("布隆过滤器初始化中...");
        try {
            addBloomFilter(this.bloomFilter);
        } catch (Exception e) {
            //布隆过滤器加载异常
            log.error(MessageConstant.BLOOM_FILTER_ADD_ERROR);
        }
    }


    /**
     * 定时任务：每天凌晨1点刷新布隆过滤器
     * cron表达式：0 0 1 * * ?
     * 含义：秒 分 时 日 月 周，每天凌晨1点触发
     * TODO: Spring的@Scheduled默认使用 单线程 执行器
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void refreshBloomFilter() {
        log.info("开始定时刷新布隆过滤器...");

        //1.创建一个新的布隆过滤器
        BloomFilter<String> newBloomFilter = BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()),
                bloomProperties.getExpectedInsertions(),
                bloomProperties.getFpp()
        );
        //TODO: 使用新的布隆过滤器加载数据
        try {
            addBloomFilter(newBloomFilter);
        } catch (Exception e) {
            //布隆过滤器加载异常
            log.error(MessageConstant.BLOOM_FILTER_ADD_ERROR);
        }


        //TODO: 获取旧的布隆过滤器的引用
        BloomFilter<String> oldBloomFilter = this.bloomFilter;

        //TODO: 让旧的布隆过滤器被GCC回收
        oldBloomFilter = null;

        /*TODO：原子切换 非常快
         * .将新的布隆过滤器赋值给原实例 (确保线程安全) 原子切换
         *   1.保证引用的原子性切换 防止其它线程引用旧的布隆过滤器 （主要）
         *   2.防止并发刷新操作 多个线程对布隆进行刷新 浪费资源
         *   3.避免使用未完全初始化的新过滤器 加了把锁，其它的线程只能使用最新的布隆了
         *   注意：在原子切换 未更新布隆过滤器引用之前 其它线程用的都是旧的布隆过滤器 只不过在切换完成后，立马就会用到新的
         */
        synchronized (this) {
            //TODO: 更新布隆过滤器的引用
            this.bloomFilter = newBloomFilter;
        }
        log.info("布隆过滤器刷新完成");
    }


    //从mysql里获取新的数据到布隆过滤器
    private void addBloomFilter(BloomFilter<String> bloomFilter) {
        //获取mysql里已启用的分类
        bloomFilter.put(RedisNameConstant.CATEGORY_CACHE + "::" + "all");
        List<Category> categoryList = categoryMapper.queryCategory(null);

        if (categoryList != null && !categoryList.isEmpty()) {
            for (Category category : categoryList) {
                //将与redis里对应的key按哈希算法放入
                bloomFilter.put(RedisNameConstant.CATEGORY_CACHE + "::" + category.getType());
            }
        }

        //获取mysql里已启用的菜品
        List<Dish> dishes = dishMapper.queryDish(new Dish());

        if (dishes != null && !dishes.isEmpty()) {
            for (Dish dish : dishes) {
                //将与redis里对应的key按哈希算法放入
                bloomFilter.put(RedisNameConstant.DISH_CACHE + "::" + dish.getCategoryId());
            }
        }

        //获取mysql里已启用的套餐
        List<SetMeal> setMeals = setMealMapper.querySetMeal(new SetMeal());

        if (setMeals != null && !setMeals.isEmpty()) {
            for (SetMeal setMeal : setMeals) {
                //将与redis里对应的key按哈希算法放入
                bloomFilter.put(RedisNameConstant.SET_MEAL_CACHE + "::" + setMeal.getCategoryId());
            }
        }
        //TODO: 判断套餐里的内容是否也要加入布隆过滤器？不需要，因为套餐已经被过滤了，里面的内容不用担心穿透
    }
}