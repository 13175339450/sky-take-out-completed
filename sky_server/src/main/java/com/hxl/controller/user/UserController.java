package com.hxl.controller.user;

import com.hxl.constant.RedisNameConstant;
import com.hxl.dto.UserLoginDTO;
import com.hxl.result.Result;
import com.hxl.service.UserService;
import com.hxl.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userUserController")
@RequestMapping("/user/user")
@Slf4j
@Api(tags = "用户端相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
    public Result logout(@RequestHeader("token") String token){
        String redisKey = RedisNameConstant.EMPLOYEE_TOKEN_CACHE + token;
        stringRedisTemplate.delete(redisKey);
        return Result.success();
    }
}
