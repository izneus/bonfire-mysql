package com.izneus.bonfire.module.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.ApiError;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 认证过的用户访问无权限资源时的异常处理
 *
 * @author Izneus
 * @date 2020/07/03
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException {
        /// 可以如下简单处理
        // httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());

        // 或者自定义response，与全局异常处理统一返回数据
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(httpServletResponse.getWriter(),
                new ApiError(
                        ErrorCode.PERMISSION_DENIED, "无权限访问资源",
                        e.getMessage()));
    }
}
