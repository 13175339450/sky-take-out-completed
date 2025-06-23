package com.hxl.interceptor;

import com.hxl.constant.JwtClaimsConstant;
import com.hxl.context.BaseContext;
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

@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 拦截执行方法之前的内容
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("开启UserInterceptor拦截器");
        //1.判断拦截到的是Controller的方法还是其它资源
        if (!(handler instanceof HandlerMethod)){
            //当前拦截到的不是动态方法 直接放行
            return true;
        }

        //2.从request里的请求头里获取token
        String token = request.getHeader(jwtProperties.getUserTokenName());

        //3.令牌校验 对token进行解析
        try {
            log.info("jwt令牌校验: {}", token);
            //token解密 如果失败(token过期等等)则会自动抛出异常
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            log.info("获取成功");
            //获取解密后的 id 员工id
            Long id = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());

            log.info("验证成功");
            //TODO: 通过ThreadLocal 将 id 传递给service层
            BaseContext.setCurrentId(id);
            log.info("当前员工id: {}", id);
            return true;
        } catch (Exception e) {
            //不通过 响应 401 状态码
            response.setStatus(401);
            return false;//拦截
        }
    }
}
