package com.hxl.mapper;

import com.hxl.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    /**
     * 根据openid查询user表里是否存在该用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User queryUserByOpenid(String openid);

    /**
     * 添加用户信息到用户表
     *
     * @param user
     */
    int insertUser(User user);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{userId}")
    User queryUserById(Long userId);
}
