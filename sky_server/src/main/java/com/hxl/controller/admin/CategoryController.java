package com.hxl.controller.admin;

import com.hxl.dto.CategoryAddDTO;
import com.hxl.dto.CategoryEditDTO;
import com.hxl.dto.CategoryPageDTO;
import com.hxl.entity.Category;
import com.hxl.result.PageResult;
import com.hxl.result.Result;
import com.hxl.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("AdminCategoryController")
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "菜品分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    @ApiOperation("分类分页查询接口")
    public Result<PageResult> categoryPage(CategoryPageDTO categoryPageDTO) {
        log.info("分类分页查询接口: {}", categoryPageDTO);

        PageResult vo = categoryService.CategoryPage(categoryPageDTO);

        return Result.success(vo);
    }

    /**
     * 新增分类： 菜品分类、套餐分类
     */
    @PostMapping
    @ApiOperation("新增分类的接口")
    public Result addCategory(@RequestBody CategoryAddDTO categoryAddDTO) {
        log.info("新增分类: {}", categoryAddDTO);

        categoryService.addCategory(categoryAddDTO);

        return Result.success();
    }

    /**
     * 启用、禁用分类
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用、进行分类")
    public Result startOrStopCategory(@PathVariable Integer status, Long id) {
        log.info("启用、禁用分类: {}, {}", status, id);

        categoryService.startOrStopCategory(status, id);

        return Result.success();
    }

    /**
     * 修改分类
     */
    @PutMapping
    @ApiOperation("修改分类")
    public Result editCategory(@RequestBody CategoryEditDTO categoryEditDTO) {
        log.info("修改分类: {}", categoryEditDTO);

        categoryService.editCategory(categoryEditDTO);

        return Result.success();
    }

    /**
     * 根据类型type查询分类
     *    TODO: 停售的分类不能被查询到
     */
    @GetMapping("list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> queryCategoryByType(Integer type){
        log.info("根据类型查询分类: {}", type);

        List<Category> voList = categoryService.queryCategoryByType(type);

        return Result.success(voList);
    }

    /**
     * 删除分类
     */
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result deleteCategory(Long id) {
        log.info("删除分类: {}", id);

        categoryService.deleteCategory(id);

        return Result.success();
    }
}
