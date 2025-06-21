package com.hxl.mapper;

import com.github.pagehelper.Page;
import com.hxl.annotation.AutoFill;
import com.hxl.entity.Category;
import com.hxl.enumeration.OperationType;
import com.hxl.vo.CategoryPageVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper {
    /**
     * 分类分页查询接口
     * @param category 动态条件封装到实体类里
     * @return 返回分页查询结果
     */
    Page<CategoryPageVO> categoryDynamicPage(Category category);

    /**
     * 新增分类
     * @param category 分类表对应的实体类
     * @return 1表示插入成功 0表示插入失败
     */
    @AutoFill(OperationType.INSERT)
    int insertCategory(Category category);

    /**
     * 通用更新方法分类的方法
     * @param category 分类实体类
     * @return 1表示更新成功 0表示更新失败
     */
    @AutoFill(OperationType.UPDATE)
    int updateCategory(Category category);

    /**
     * 通用的查询分类的方法
     * @param category 封装查询条件
     * @return 返回查询结果集合
     */
    List<Category> queryCategory(Category category);

    /**
     * 根据分类id查询分类的 售卖状态
     * @param id 分类id
     * @return 1表示启售中 0表示禁售中
     */
    @Select("select status from category where id = #{id}")
    Integer queryStatus(Long id);

    int deleteCategory(Category category);
}
