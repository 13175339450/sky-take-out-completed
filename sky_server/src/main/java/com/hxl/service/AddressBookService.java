package com.hxl.service;

import com.hxl.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    /**
     * 获取当前用户的所有地址簿信息
     * @param userId 用户id
     * @return 返回查询到的地址簿信息
     */
    List<AddressBook> getAddressBookList(Long userId);

    /**
     * 新增地址
     * @param addressBook 地址信息
     */
    void addAddress(AddressBook addressBook);

    /**
     * 设置默认地址
     * @param id 地址id
     */
    void setDefaultAddress(Long id);

    /**
     * 查询当前用户的默认地址
     * @param userId  用户id
     * @return 返回默认地址的信息
     */
    AddressBook getDefaultAddress(Long userId);

    /**
     * 根据id查询地址
     * @param id 地址id
     * @return
     */
    AddressBook queryAddressById(Long id);

    /**
     * 根据地址id删除地址
     * @param id 地址id
     */
    void deleteAddressById(Long id);

    /**
     * 根据id修改地址
     * @param addressBook 修改后的地址信息
     */
    void updateAddress(AddressBook addressBook);
}
