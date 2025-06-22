package com.hxl.controller.admin;

import com.hxl.result.Result;
import com.hxl.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺相关接口")
public class ShopController {

    @Autowired
    private ShopService shopService;

    /**
     * 设置店铺营业状态
     */
    @PutMapping("/{status}")
    @ApiOperation("设置营业状态")
    public Result setShopStatus(@PathVariable Integer status) {
        log.info("设置店铺营业状态: {}", status == 1 ? "营业中" : "打烊中");

        shopService.setShopStatus(status);

        return Result.success();
    }

    /**
     * 获取店铺营业状态
     */
    @GetMapping("status")
    @ApiOperation("获取店铺营业状态")
    public Result<Integer> getShopStatus() {
        Integer status = shopService.getShopStatus();

        //TODO: bug 当第一次连接redis时，查询到status为null 需要将状态设置为0 避免异常
        status = status == null ? 0 : status;

        log.info("获取店铺营业状态: {}", status == 1 ? "营业中" : "打烊中");

        return Result.success(status);
    }
}
