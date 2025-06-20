package com.hxl.service;

import com.hxl.dto.CategoryAddDTO;
import com.hxl.dto.CategoryEditDTO;
import com.hxl.dto.CategoryPageDTO;
import com.hxl.entity.Category;
import com.hxl.result.PageResult;

import java.util.List;

public interface CategoryService {
    /**
     * 分类分页查询接口
     * @param categoryPageDTO 分类分页信息DTO实体类
     * @return 返回 分页查询的结果封装类
     */
    PageResult CategoryPage(CategoryPageDTO categoryPageDTO);

    /**
     * 新增分类的接口
     * @param categoryAddDTO 分类相关信息的封装DTO类
     */
    void addCategory(CategoryAddDTO categoryAddDTO);

    /**
     * 启用、禁用分类
     * @param status 分类状态
     * @param id 分类id
     */
    void startOrStopCategory(Integer status, Long id);

    /**
     * 修改分类
     * @param categoryEditDTO 新的分类信息的DTO
     */
    void editCategory(CategoryEditDTO categoryEditDTO);

    /**
     * 根据类型查询分类
     * @param type 分类类型
     * @return 获取该type类型的所有分类的集合
     */
    List<Category> queryCategoryByType(Integer type);
}
