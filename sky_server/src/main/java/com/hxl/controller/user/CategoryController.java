package com.hxl.controller.user;

import com.hxl.entity.Category;
import com.hxl.result.Result;
import com.hxl.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 条件查询:
     *    查询小程序左侧分类栏
     */
    @GetMapping("/list")
    @ApiOperation("条件查询")
    public Result<List<Category>> queryCategory(Integer type){
        log.info("用户端条件查询: {}", type);

        //不显示停售的分类
        List<Category> vo = categoryService.queryCategoryByType(type);

        return Result.success(vo);
    }
}
