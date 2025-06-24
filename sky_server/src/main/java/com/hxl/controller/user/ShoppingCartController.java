package com.hxl.controller.user;

import com.hxl.context.BaseContext;
import com.hxl.dto.ShoppingCartAddOrSubDTO;
import com.hxl.entity.ShoppingCart;
import com.hxl.result.Result;
import com.hxl.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userShoppingCartController")
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "购物车相关接口")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     */
    @PostMapping("/add")
    @ApiOperation("添加购物车的相关接口")
    public Result addShoppingCart(@RequestBody ShoppingCartAddOrSubDTO shoppingCartAddOrSubDTO) {
        log.info("添加购物车的相关接口: {}", shoppingCartAddOrSubDTO);

        shoppingCartService.addShoppingCart(shoppingCartAddOrSubDTO);

        return Result.success();
    }

    /**
     * 查看当前用户的购物车
     */
    @GetMapping("/list")
    @ApiOperation("查看当前用户的购物车")
    public Result<List<ShoppingCart>> catShoppingCart() {
        log.info("查看当前用户的购物车: userId为 {}", BaseContext.getCurrentId());

        List<ShoppingCart> vo = shoppingCartService.catShoppingCart();

        return Result.success(vo);
    }

    /**
     * 删除购物车中的一个商品
     */
    @PostMapping("/sub")
    @ApiOperation("删除购物车中的一个商品")
    public Result subShopping(@RequestBody ShoppingCartAddOrSubDTO shoppingCartAddOrSubDTO) {
        log.info("删除购物车中的一个商品: {}", shoppingCartAddOrSubDTO);

        shoppingCartService.subShoppingCart(shoppingCartAddOrSubDTO);

        return Result.success();
    }

    /**
     * 清空购物车
     */
    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result cleanShoppingCart(){
        log.info("清空购物车");

        shoppingCartService.cleanShoppingCart();

        return Result.success();
    }
}
