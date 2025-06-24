package com.hxl.controller.user;

import com.hxl.context.BaseContext;
import com.hxl.entity.AddressBook;
import com.hxl.result.Result;
import com.hxl.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userAddressBookController")
@RequestMapping("/user/addressBook")
@Slf4j
@Api(tags = "地址簿接口的相关接口")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 查询当前用户用的所有地址信息
     */
    @GetMapping("/list")
    @ApiOperation("查询当前用户用的所有地址信息")
    public Result<List<AddressBook>> queryAddressBook() {
        //获取当前用户的id
        Long userId = BaseContext.getCurrentId();
        log.info("查询当前用户用的所有地址信息: userId为 {}", userId);

        List<AddressBook> vo = addressBookService.getAddressBookList(userId);

        return Result.success(vo);
    }

    /**
     * 新增地址
     */
    @PostMapping
    @ApiOperation("新增地址")
    public Result addAddress(@RequestBody AddressBook addressBook) {
        log.info("新增地址: {}", addressBook);

        addressBookService.addAddress(addressBook);

        return Result.success();
    }

    /**
     * 设置默认地址
     */
    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result setDefaultAddress(@RequestBody AddressBook addressBook) {
        log.info("设置默认地址: {}", addressBook);

        addressBookService.setDefaultAddress(addressBook.getId());

        return Result.success();
    }

    /**
     * 查询默认地址
     */
    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefaultAddress() {
        Long userId = BaseContext.getCurrentId();
        log.info("查询默认地址: userId为 {}", userId);

        AddressBook vo = addressBookService.getDefaultAddress(userId);

        return Result.success(vo);
    }

    /**
     * 根据id查询地址 信息回显
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> queryAddressById(@PathVariable Long id) {
        log.info("根据id查询地址: {}", id);

        AddressBook vo = addressBookService.queryAddressById(id);

        return Result.success(vo);
    }

    /**
     * 根据地址id删除地址
     */
    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result deleteAddress(Long id) {
        log.info("根据id删除地址: {}", id);

        addressBookService.deleteAddressById(id);

        return Result.success();
    }

    /**
     * 根据id修改地址
     */
    @PutMapping
    @ApiOperation("根据id修改地址")
    public Result updateAddress(@RequestBody AddressBook addressBook){
        log.info("根据id修改地址: {}", addressBook);

        addressBookService.updateAddress(addressBook);

        return Result.success();
    }
}
