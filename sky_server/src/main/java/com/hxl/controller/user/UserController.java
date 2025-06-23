package com.hxl.controller.user;

import com.hxl.dto.UserLoginDTO;
import com.hxl.result.Result;
import com.hxl.service.UserService;
import com.hxl.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userUserController")
@RequestMapping("/user/user")
@Slf4j
@Api(tags = "用户端相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("login")
    @ApiOperation("用户登录")
    public Result<UserLoginVO> userLogin(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录: {}", userLoginDTO);

        UserLoginVO vo = userService.wxLogin(userLoginDTO.getCode());

        return Result.success(vo);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    @ApiOperation("用户退出登录")
    public Result logout(){
        return Result.success();
    }
}
