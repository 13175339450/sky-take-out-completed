package com.hxl.mapper;

import com.hxl.entity.AddressBook;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AddressBookMapper {
    /**
     * 根据用户id查询用户的所有地址信息
     * @param userId 用户id
     * @return 返回查询到的地址簿
     */
    @Select("select * from address_book where user_id = #{userId}")
    List<AddressBook> queryAddressBookListByUserId(Long userId);

    /**
     * 新增地址
     * @param addressBook 地址信息
     * @return 1表示新增成功 0表示新增失败
     */
    int insertAddressBook(AddressBook addressBook);

    /**
     * 更改地址的 默认状态
     * @param addressBook 地址信息
     * @return 1表示修改成功 0表示修改失败
     */
    int updateDefaultAddress(AddressBook addressBook);

    /**
     * 查询地址信息 通用
     * @param addressBook 查询条件
     * @return 返回地址信息
     */
    AddressBook querySingleAddress(AddressBook addressBook);

    /**
     * 动态删除地址的语句
     * @param addressBook 条件封装
     * @return 0表示未删除 反之为删除的行数
     */
    int deleteAddress(AddressBook addressBook);

    /**
     * 修改地址
     * @param addressBook 新的地址信息
     */
    int updateAddress(AddressBook addressBook);
}
