package com.hxl.controller.user;

import com.hxl.entity.SetMeal;
import com.hxl.result.Result;
import com.hxl.service.SetMealService;
import com.hxl.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

@RestController("userSetMealController")
@RequestMapping("/user/setmeal")
@Slf4j
@Api(tags = "用户套餐相关的接口")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;


    /**
     * 根据分类id查询套餐
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    public Result<List<SetMeal>> querySetMealByCategoryId(Long categoryId) {
        log.info("根据分类id查询套餐: {}", categoryId);

        //查询起售中的套餐
        List<SetMeal> vo = setMealService.querySetMealByCategoryId(categoryId);

        return Result.success(vo);
    }

    /**
     * 根据套餐id查询套餐包含的菜品
     */
    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品")
    public Result<List<DishItemVO>> queryDishBySetMealId(@PathVariable Long id) {
        log.info("根据套餐id查询包含的菜品: {}", id);

        //TODO: 可以加上停售的 (服务员替换)
        List<DishItemVO> vo = setMealService.queryDishBySetMealId(id);

        return Result.success(vo);
    }
}
