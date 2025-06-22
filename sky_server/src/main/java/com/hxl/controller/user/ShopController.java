package com.hxl.controller.user;

import com.hxl.result.Result;
import com.hxl.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
@Api(tags = "用户端的商家的相关接口")
public class ShopController {

    @Autowired
    private ShopService shopService;

    /**
     * 获取商家status
     */
    @GetMapping("/status")
    @ApiOperation("获取商家的状态")
    public Result<Integer> getShopStatus(){
        Integer shopStatus = shopService.getShopStatus();

        shopStatus = shopStatus == null ? 0 : shopStatus;

        return Result.success(shopStatus);
    }
}
