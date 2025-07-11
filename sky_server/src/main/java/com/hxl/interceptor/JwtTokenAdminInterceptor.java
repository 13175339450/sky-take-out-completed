package com.hxl.interceptor;

import com.hxl.constant.JwtClaimsConstant;
import com.hxl.constant.MessageConstant;
import com.hxl.constant.RedisNameConstant;
import com.hxl.context.BaseContext;
import com.hxl.exception.TokenCheckException;
import com.hxl.properties.JwtProperties;
import com.hxl.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 定义管理端的 jwt令牌校验拦截器
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
        log.info("开启AdminHandler拦截器");
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

        //TODO：优先查缓存 设置try-catch 防止redis异常
        String redisKey = RedisNameConstant.EMPLOYEE_TOKEN_CACHE + token;
        try {
            // 先尝试从Redis获取（新增代码）
            String cachedId = stringRedisTemplate.opsForValue().get(redisKey);
            if (cachedId != null) {
                //将用户id 从String转换为Long
                Long id = Long.parseLong(cachedId);
                //存入ThreadLocal里
                BaseContext.setCurrentId(id);
                log.info("*************Redis验证成功，员工id: {}*****************", id);
                return true; // 缓存存在直接放行
            }
        } catch (Exception e) {
            log.info("Redis解析异常！继续JWT解析");
        }

        //校验令牌
        try {
            log.info("jwt令牌校验: {}", token);
            // token解密 根据加密密钥secretKey 解密 token 如果失败(token过期等等)则会自动抛出异常
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            log.info("获取成功");
            //获取解密后的 id 员工id
            Long id = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());

            // TODO: 解析成功后将token重新存入Redis（新增代码）
            stringRedisTemplate.opsForValue().set(
                    redisKey,
                    id.toString(),
                    jwtProperties.getAdminTtl(),
                    TimeUnit.MILLISECONDS
            );

            log.info("验证成功");
            //TODO: 通过ThreadLocal 将 id 传递给service层 调用封装的工具类
            BaseContext.setCurrentId(id);

            log.info("当前员工id: {}", id);
            //token有效 放行
            return true;
        } catch (Exception e) {
            //不通过 响应 401 状态码
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
