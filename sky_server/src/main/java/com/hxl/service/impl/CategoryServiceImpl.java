package com.hxl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hxl.constant.MessageConstant;
import com.hxl.constant.StatusConstant;
import com.hxl.dto.CategoryAddDTO;
import com.hxl.dto.CategoryEditDTO;
import com.hxl.dto.CategoryPageDTO;
import com.hxl.entity.Category;
import com.hxl.exception.DeleteFailException;
import com.hxl.mapper.CategoryMapper;
import com.hxl.mapper.DishMapper;
import com.hxl.mapper.SetMealMapper;
import com.hxl.result.PageResult;
import com.hxl.service.CategoryService;
import com.hxl.vo.CategoryPageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetMealMapper setMealMapper;


    /**
     * 分类分页查询
     */
    @Override
    public PageResult CategoryPage(CategoryPageDTO categoryPageDTO) {
        //1.设置分页参数
        PageHelper.startPage(categoryPageDTO.getPage(), categoryPageDTO.getPageSize());

        //2.封装实体类 去动态查询分类分页数据 属性拷贝
        Category category = new Category();
        BeanUtils.copyProperties(categoryPageDTO, category);

        //3.进行分页查询
        Page<CategoryPageVO> page = categoryMapper.categoryDynamicPage(category);

        //4.结果封装
        long total = page.getTotal();
        List<CategoryPageVO> records = page.getResult();

        return new PageResult(total, records);
    }

    /**
     * 新增分类
     */
    @Override
    public void addCategory(CategoryAddDTO categoryAddDTO) {
        //实体类
        Category category = new Category();

        //属性拷贝
        BeanUtils.copyProperties(categoryAddDTO, category);

        //设置默认的status
        category.setStatus(StatusConstant.CATEGORY_DEFAULT_STATUS);

        //执行新增操作
        int row = categoryMapper.insertCategory(category);
    }

    /**
     * 启用、禁用分类
     */
    @Override
    public void startOrStopCategory(Integer status, Long id) {
        //封装实体类
        Category category = Category.builder().id(id).status(status).build();

        //利用通用的update方法
        int row = categoryMapper.updateCategory(category);
    }

    /**
     * 修改分类
     */
    @Override
    public void editCategory(CategoryEditDTO categoryEditDTO) {
        //封装实体类 进行属性拷贝
        Category category = new Category();
        BeanUtils.copyProperties(categoryEditDTO, category);

        //调用通用update方法
        categoryMapper.updateCategory(category);
    }

    /**
     * 根据分类类型查询分类集合
     */
    @Override
    public List<Category> queryCategoryByType(Integer type) {

        //检查Redis里是否

        //封装实体对象
        Category category = Category.builder().type(type).build();

        //定义通用查询方法 停售的不能被查询
        return categoryMapper.queryCategory(category);
    }

    /**
     * 删除分类
     * TODO: 删除分类需要满足如下条件: 逻辑删除
     *          1.该分类必须是 停售状态
     *          2.该分类没有绑定菜品、套餐
     */
    @Override
    public void deleteCategory(Long id) {
        //1.获取分类的status --- 查询具体的字段 效率更高
        Integer status = categoryMapper.queryStatus(id);

        if (status.equals(StatusConstant.CATEGORY_DEFAULT_STATUS)){
            //启售中的分类不能删除
            throw new DeleteFailException(MessageConstant.DELETE_FAIL_START_CATEGORY);
        }

        //2.检查分类是否绑定了菜品
        Integer amount = dishMapper.getCategoryBindDishAmount(id);

        if (amount > 0){
            //绑定菜品的分类不能删除
            throw new DeleteFailException(MessageConstant.DELETE_FAIL_CATEGORY_BIND_DISH);
        }

        //3.检查分类是否绑定了套餐
        amount = setMealMapper.getCategoryBindSetMeal(id);

        if (amount > 0){
            //绑定套餐的分类不能删除
            throw new DeleteFailException(MessageConstant.DELETE_FAIL_CATEGORY_BIND_SET_MEAL);
        }

        //4.执行删除操作 通用的删除方法
        Category category = Category.builder().id(id).build();
        int row = categoryMapper.deleteCategory(category);
    }
}
