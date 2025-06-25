package com.hxl.constant;

/**
 * TODO：redis优先对热点数据进行缓存
 *      1.修改信息是不常用的，所以对于修改信息时的主键回显不需要进行redis缓存
 *      2.
 */
public class RedisNameConstant {
    //店铺状态的前缀
    public static final String SHOP_STATUS = "shop-status";

    //分类缓存 + null
    public static final String CATEGORY_CACHE = "category";

    //菜品缓存 + categoryId
    public static final String DISH_CACHE = "dish";

    //套餐缓存 + categoryId
    public static final String SET_MEAL_CACHE = "setMeal";

    //套餐-菜品缓存 + setMealId
    public static final String SET_MEAL_DISH_CACHE = "setMealDish";

    //所有地址簿信息 + userId
    public static final String ADDRESS_BOOK_ALL = "addressBookAll";

    //当前用户的默认地址 + userId
    public static final String DEFAULT_ADDRESS = "defaultAddress";
}
