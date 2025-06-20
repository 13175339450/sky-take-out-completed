package com.hxl.interceptor;

import com.hxl.constant.JwtClaimsConstant;
import com.hxl.constant.MessageConstant;
import com.hxl.context.BaseContext;
import com.hxl.exception.TokenCheckException;
import com.hxl.properties.JwtProperties;
import com.hxl.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 定义管理端的 jwt令牌校验拦截器
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 执行 Handler方法前的 拦截器
     *
     * @param request  请求体
     * @param response 响应体
     * @param handler  我们要调用的方法对象
     * @return true为放行
     * @throws Exception 抛出的异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("开启Handler拦截器");
        //1.判断当前拦截到的是Controller的方法还是其它资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法 直接放行
            return true;
        }

        /* 从请求体的Header请求头里获取token
         * 根据Token名 在header里获取
         * TokenName : token  kv键值对形式
         */
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        //校验令牌
        try {
            log.info("jwt令牌校验: {}", token);
            // token解密 根据加密密钥secretKey 解密 token
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            log.info("获取成功");
            //获取解密后的 id 员工id
            Long id = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());

            log.info("验证成功");
            //TODO: 通过ThreadLocal 将 id 传递给service层 调用封装的工具类
            BaseContext.setCurrentId(id);

            // 手动验证 token 有效期
//            Date expiration = claims.getExpiration();
//            if (expiration == null) {
//                //token缺少有效时间
//                throw new TokenCheckException(MessageConstant.TOKEN_LACK_TIME);
//            }
//            if (new Date().after(expiration)) {
//                //token已过期
//                throw new TokenCheckException(MessageConstant.TOKEN_OUT_OF_DATE);
//            }

            log.info("当前员工id: {}", id);
            //token有效 放行
            return true;
        } catch (Exception e) {
            //不通过 相应 401 状态码
            response.setStatus(401);
            return false;//拦截
        }
    }


    /*
    //preHandle放行后 执行 可以用于 敏感词过滤
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //执行完
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
    */
}
