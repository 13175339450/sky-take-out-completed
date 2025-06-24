package com.hxl.controller.user;

import com.hxl.dto.ShoppingCartAddDTO;
import com.hxl.entity.ShoppingCart;
import com.hxl.result.Result;
import com.hxl.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShoppingCartController")
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "购物车相关接口")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("添加购物车的相关接口")
    public Result addShoppingCart(@RequestBody ShoppingCartAddDTO shoppingCartAddDTO) {
        log.info("添加购物车的相关接口: {}", shoppingCartAddDTO);

        shoppingCartService.addShoppingCart(shoppingCartAddDTO);

        return Result.success();
    }
}
