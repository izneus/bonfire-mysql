package com.izneus.bonfire.module.system.service.impl;

import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.BadRequestException;
import com.izneus.bonfire.common.util.RedisUtils;
import com.izneus.bonfire.module.security.JwtUser;
import com.izneus.bonfire.module.security.JwtUtils;
import com.izneus.bonfire.module.system.controller.v1.query.LoginQuery;
import com.izneus.bonfire.module.system.service.LoginService;
import com.izneus.bonfire.module.system.service.dto.CaptchaDTO;
import com.izneus.bonfire.module.system.service.dto.LoginDTO;
import com.wf.captcha.SpecCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.izneus.bonfire.common.constant.Constant.REDIS_KEY_TYPE_CAPTCHA;

/**
 * @author Izneus
 * @date 2020/07/01
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;

    @Override
    public LoginDTO login(LoginQuery loginQuery) {
        // todo 5分钟内连续输错5次密码，锁定账号30分钟

        // 查询验证码
        String key = REDIS_KEY_TYPE_CAPTCHA + loginQuery.getCaptchaId();
        String captcha = (String) redisUtils.get(key);
        // 查询过的验证码及时清除
        redisUtils.del(key);
        if (!StringUtils.hasText(captcha)) {
            throw new BadRequestException(ErrorCode.INVALID_ARGUMENT, "验证码已过期");
        }
        if (!captcha.equalsIgnoreCase(loginQuery.getCaptcha())) {
            throw new BadRequestException(ErrorCode.INVALID_ARGUMENT, "验证码错误");
        }

        // 调用spring security的账号密码验证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginQuery.getUsername(), loginQuery.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 登录成功，生成jwt
        String token = jwtUtils.createToken(authentication);
        final JwtUser user = (JwtUser) authentication.getPrincipal();

        // todo 保存权限到redis

        // todo single login

        return new LoginDTO(user.getUsername(), token);
    }

    @Override
    public CaptchaDTO getCaptcha() {
        // 生成验证码
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String value = specCaptcha.text().toLowerCase();
        String uuid = UUID.randomUUID().toString();
        String key = REDIS_KEY_TYPE_CAPTCHA + uuid;
        // 保存验证码到redis缓存，2分钟后过期
        redisUtils.set(key, value, 2L, TimeUnit.MINUTES);
        return new CaptchaDTO(uuid, specCaptcha.toBase64());
    }
}
