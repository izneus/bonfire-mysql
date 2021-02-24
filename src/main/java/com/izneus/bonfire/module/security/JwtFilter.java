package com.izneus.bonfire.module.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Izneus
 * @date 2020/07/03
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(httpServletRequest);
        if (StringUtils.hasText(token)) {
            // todo jwt保存在redis，扩展实现黑名单等功能
            Authentication authentication = jwtUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // todo jwt续期
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 初步校验Token类型
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtConfig.getHeader());
        // 判断token类型
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtConfig.getType())) {
            // 去掉令牌前缀，注意type后面的空格
            return bearerToken.replace(jwtConfig.getType(), "").trim();
        } else {
            log.debug("非法Token：{}", bearerToken);
        }
        return null;
    }
}
