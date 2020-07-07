package com.izneus.bonfire.module.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.ApiError;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 解决匿名用户访问无权限资源时的异常
 *
 * @author Izneus
 * @date 2020/07/03
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        /// 无凭据访问资源会调用，可以简单如下实现
        /*httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                e == null ? "Unauthorized" : e.getMessage());*/

        // 或者自定义response，与全局异常处理统一返回数据
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(httpServletResponse.getWriter(),
                new ApiError(
                        ErrorCode.UNAUTHENTICATED, "由于令牌丢失、无效或过期，请求未通过身份验证",
                        e.getMessage()));
    }
}
