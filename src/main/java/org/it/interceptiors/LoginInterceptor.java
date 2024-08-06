package org.it.interceptiors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.it.utils.JwtUtil;
import org.it.utils.ThreadLocalUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 *  登录拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    // 请求到达接口前执行；
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 令牌获取
        String token = request.getHeader("Authorization");
        // 验证token
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 把业务数据存放到ThreadLocal中
            ThreadLocalUtil.set(claims);
            // 放行
            return true;
        } catch (Exception e) {
            // http响应状态码401
            response.setStatus(401);
            // 不放行
            return false;
        }

    }

    // 请求结束后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清空ThreadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
