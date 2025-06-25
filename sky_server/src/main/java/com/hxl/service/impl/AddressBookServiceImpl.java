package com.hxl.service.impl;

import com.hxl.constant.RedisNameConstant;
import com.hxl.constant.StatusConstant;
import com.hxl.context.BaseContext;
import com.hxl.entity.AddressBook;
import com.hxl.mapper.AddressBookMapper;
import com.hxl.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 获取当前用户的所有地址簿信息
     */
    @Cacheable(cacheNames = RedisNameConstant.ADDRESS_BOOK_ALL, key = "#userId")
    @Override
    public List<AddressBook> getAddressBookList(Long userId) {
        //根据userId查询用户的所有地址簿信息
        return addressBookMapper.queryAddressBookListByUserId(userId);
    }

    /**
     * 新增地址
     */
    @CacheEvict(cacheNames = RedisNameConstant.ADDRESS_BOOK_ALL, allEntries = true)
    @Override
    public void addAddress(AddressBook addressBook) {
        //手动配置userId
        addressBook.setUserId(BaseContext.getCurrentId());

        //新增的地址为非默认地址
        addressBook.setIsDefault(StatusConstant.NOT_DEFAULT_ADDRESS);

        //新增地址
        addressBookMapper.insertAddressBook(addressBook);
    }

    /**
     * 设置默认地址
     * @param id 地址id
     */
    @Override
    //删除地址簿和默认地址缓存
    @CacheEvict(cacheNames = {RedisNameConstant.ADDRESS_BOOK_ALL, RedisNameConstant.DEFAULT_ADDRESS},
            allEntries = true)
    public void setDefaultAddress(Long id) {
        //默认地址只能有一个，所以设置之前把当前用户的所有地址都设置为 非默认
        // 利用实体类 不存入地址id 将所有地址的状态设置为非默认
        AddressBook addressBook = AddressBook.builder()
                .userId(BaseContext.getCurrentId())
                .isDefault(StatusConstant.NOT_DEFAULT_ADDRESS).build();
        addressBookMapper.updateAddress(addressBook);

        //添加指定地址状态
        addressBook.setId(id);
        addressBook.setIsDefault(StatusConstant.DEFAULT_ADDRESS);
        //执行修改操作
        addressBookMapper.updateAddress(addressBook);
    }

    /**
     * 查询当前用户的默认地址
     */
    @Override
    @Cacheable(cacheNames = RedisNameConstant.DEFAULT_ADDRESS, key = "#userId")
    public AddressBook getDefaultAddress(Long userId) {
        AddressBook addressBook = AddressBook.builder().userId(userId).isDefault(StatusConstant.DEFAULT_ADDRESS).build();
        //
        return addressBookMapper.querySingleAddress(addressBook);
    }

    /**
     * 根据id回显地址信息
     */
    @Override
    public AddressBook queryAddressById(Long id) {
        AddressBook addressBook = AddressBook.builder().id(id).userId(BaseContext.getCurrentId()).build();

        return addressBookMapper.querySingleAddress(addressBook);
    }

    /**
     * 根据地址id删除地址
     */
    @Override
    //删除地址簿和默认地址缓存
    @CacheEvict(cacheNames = {RedisNameConstant.ADDRESS_BOOK_ALL, RedisNameConstant.DEFAULT_ADDRESS},
            allEntries = true)
    public void deleteAddressById(Long id) {
        AddressBook addressBook = AddressBook.builder().id(id).userId(BaseContext.getCurrentId()).build();

        //动态删除语句
        addressBookMapper.deleteAddress(addressBook);
    }

    /**
     * 修改地址
     */
    @Override
    //删除地址簿和默认地址缓存
    @CacheEvict(cacheNames = {RedisNameConstant.ADDRESS_BOOK_ALL, RedisNameConstant.DEFAULT_ADDRESS},
            allEntries = true)
    public void updateAddress(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());

        addressBookMapper.updateAddress(addressBook);
    }
}
