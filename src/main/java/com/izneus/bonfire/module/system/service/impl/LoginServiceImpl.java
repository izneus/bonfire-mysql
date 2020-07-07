package com.izneus.bonfire.module.system.service.impl;

import com.izneus.bonfire.module.security.JwtUtils;
import com.izneus.bonfire.module.system.controller.v1.query.LoginQuery;
import com.izneus.bonfire.module.system.service.LoginService;
import com.izneus.bonfire.module.system.service.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * @author Izneus
 * @date 2020/07/01
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtils jwtUtils;

    @Override
    public LoginDTO login(LoginQuery loginQuery) {
        // todo 验证码

        // 集成spring security的账号密码验证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginQuery.getUsername(), loginQuery.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 登录成功，生成jwt
        String token = jwtUtils.createToken(authentication);
        final User user = (User) authentication.getPrincipal();

        // todo single login

        return new LoginDTO(user.getUsername(), token);
    }
}
