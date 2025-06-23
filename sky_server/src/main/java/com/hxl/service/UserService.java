package com.hxl.service;

import com.hxl.vo.UserLoginVO;

public interface UserService {
    /**
     * 用户登录
     * @param code 微信授权码
     * @return 返回前端响应的response对象
     */
    UserLoginVO wxLogin(String code);
}
