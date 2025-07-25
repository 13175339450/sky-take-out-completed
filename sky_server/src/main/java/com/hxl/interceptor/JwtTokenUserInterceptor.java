package com.hxl.interceptor;

import com.hxl.constant.JwtClaimsConstant;
import com.hxl.constant.RedisNameConstant;
import com.hxl.context.BaseContext;
import com.hxl.properties.JwtProperties;
import com.hxl.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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

        //TODO：优先查缓存 设置try-catch 防止redis异常
        String redisKey = RedisNameConstant.CLIENT_TOKEN_CACHE + token;
        try {
            // 先尝试从Redis获取（新增代码）
            String cachedId = stringRedisTemplate.opsForValue().get(redisKey);
            if (cachedId != null) {
                //将用户id 从String转换为Long
                Long id = Long.parseLong(cachedId);
                //存入ThreadLocal里
                BaseContext.setCurrentId(id);
                log.info("*************Redis验证成功，客户id: {}*****************", id);
                return true; // 缓存存在直接放行
            }
        } catch (Exception e) {
            log.info("Redis解析异常！继续JWT解析");
        }

        //3.令牌校验 对token进行解析
        try {
            log.info("jwt令牌校验: {}", token);
            //token解密 如果失败(token过期等等)则会自动抛出异常
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            log.info("获取成功");
            //获取解密后的 id 员工id
            Long id = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());

            // TODO: 解析成功后将token重新存入Redis（新增代码）
            stringRedisTemplate.opsForValue().set(
                    redisKey,
                    id.toString(),
                    jwtProperties.getAdminTtl(),
                    TimeUnit.MILLISECONDS
            );

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
