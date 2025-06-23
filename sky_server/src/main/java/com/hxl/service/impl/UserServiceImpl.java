package com.hxl.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hxl.constant.JwtClaimsConstant;
import com.hxl.constant.MessageConstant;
import com.hxl.constant.WeChatConstant;
import com.hxl.entity.User;
import com.hxl.exception.LoginFailException;
import com.hxl.mapper.UserMapper;
import com.hxl.properties.JwtProperties;
import com.hxl.properties.WeChatProperties;
import com.hxl.service.UserService;
import com.hxl.utils.HttpClientUtil;
import com.hxl.utils.JwtUtil;
import com.hxl.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    //微信服务接口地址
    public static final String WX_LOGIN="http://api.weixin.qq.com/sns/jscode2session";


    /**
     * 用户登录:
     *  1.读取配置类里的用户配置信息 然后进行封装
     *  2.利用封装的数据调用工具类里的doGet方法发送请求，然后获取返回的response对象
     *  3.从response对象里获取openid 如果配置信息正确，会获取微信用户的唯一标识
     *  4.再通过openid判断是否该用户已是老用户 (查询user表)
     *  5.是新用户则自动注册该用户
     *  6.使用Jwt令牌的工具里 结合密钥、过期时间和用户在user表里的id值 组合创建token 并返回
     */
    @Override
    public UserLoginVO wxLogin(String code) {
        //1.根据自己封装的HttpClientUtil工具包获取openid
        //1.1封装 读取配置文件里的相关数据
        Map map = new HashMap();
        map.put(WeChatConstant.APP_ID, weChatProperties.getAppid());
        map.put(WeChatConstant.SECRET, weChatProperties.getSecret());
        map.put(WeChatConstant.JS_CODE, code);
        map.put(WeChatConstant.GRANT_TYPE, WeChatConstant.GRANT_TYPE_VALUE);
        /* JSON.parseObject是 FastJSON 中用于 JSON 字符串解析的核心方法，
         * 能将字符串转换为可操作的 JSON 对象或 Java 实体类，
         * 是处理 API 响应、数据交互的关键工具。
         * 使用时需确保 JSON 格式正确，并根据业务需求选择合适的解析方式（JSONObject或 Java 对象）。*/
        //1.2调用工具类 执行get方法
        String json = HttpClientUtil.doGet(WeChatConstant.LOGIN_URL, map);
        //1.3将json字符串进行格式转换
        JSONObject jsonObject = JSON.parseObject(json);
        //1.4获取openid
        String openid = jsonObject.getString("openid");

        //2.判断openid是否为空 空表示没有该用户 登录失败
        if (openid == null){
            throw new LoginFailException(MessageConstant.LOGIN_FAIL);
        }

        //3.判断当前用户是否为新用户 从user表里查找是否存在该用户 根据openid(每个用户都唯一且不变)
        User user = userMapper.queryUserByOpenid(openid);

        //4.如果是新用户 自动完成注册
        if (user == null){
            //4.1封装用户信息
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            //4.2查询用户数据到user表
            userMapper.insertUser(user);
        }

        //5.创建Token对象
        //5.1封装claims数据 userId
        Map claims = new HashMap();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        //5.2利用 SecretKey + Ttl + userId通过算法组合成 token
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        //6.将结果进行封装
        return UserLoginVO.builder()
                .id(user.getId())
                .openid(openid)
                .token(token).build();
    }

}
