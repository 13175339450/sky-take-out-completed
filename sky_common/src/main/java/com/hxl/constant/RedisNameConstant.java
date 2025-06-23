package com.hxl.constant;

public class RedisNameConstant {
    //店铺状态的前缀
    public static final String SHOP_STATUS = "shop-status";

    //分类缓存 + null
    public static final String CATEGORY_CACHE = "category-";

    //菜品缓存 + categoryId
    public static final String DISH_CACHE = "dish-";

    //套餐缓存 + categoryId
    public static final String SET_MEAL_CACHE = "setMeal-";

    //套餐-菜品缓存 + setMealId
    public static final String SET_MEAL_DISH_CACHE = "setMealDish-";
}
