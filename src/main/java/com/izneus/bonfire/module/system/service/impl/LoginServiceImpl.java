package com.izneus.bonfire.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.izneus.bonfire.common.constant.Dict;
import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.BadRequestException;
import com.izneus.bonfire.common.util.RedisUtils;
import com.izneus.bonfire.config.BonfireProperties;
import com.izneus.bonfire.module.security.JwtUtils;
import com.izneus.bonfire.module.system.controller.v1.query.LoginQuery;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.mapper.SysUserMapper;
import com.izneus.bonfire.module.system.service.LoginService;
import com.izneus.bonfire.module.system.service.dto.CaptchaDTO;
import com.izneus.bonfire.module.system.service.dto.ListAuthorityDTO;
import com.izneus.bonfire.module.system.service.dto.LoginDTO;
import com.wf.captcha.SpecCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.izneus.bonfire.common.constant.Constant.MAX_PASSWORD_RETRY_COUNT;
import static com.izneus.bonfire.common.constant.Constant.REDIS_KEY_TYPE_CAPTCHA;

/**
 * @author Izneus
 * @date 2020/07/01
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final JwtUtils jwtUtils;
    private final RedisUtils redisUtils;
    private final SysUserMapper sysUserMapper;
    private final BonfireProperties bonfireProperties;

    @Value("${jwt.expire}")
    private Long jwtExpire;

    @Override
    public LoginDTO login(LoginQuery loginQuery) {
        // 检查连续密码输入错误次数
        String retryKey = "user:" + loginQuery.getUsername() + ":password-retry-count";
        int passwordRetryCount = (int) Optional.ofNullable(redisUtils.get(retryKey)).orElse(0);
        if (passwordRetryCount >= MAX_PASSWORD_RETRY_COUNT) {
            throw new BadRequestException(ErrorCode.PERMISSION_DENIED,
                    "因连续密码输入错误，该用户已被锁定，请30分钟之后重试");
        }

        if (bonfireProperties.getCaptchaEnabled()) {
            /// 查询验证码
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
        }

        // todo 实现spring-security相关类深度整合
        /// 暂时停用，简单调用spring-security的账号密码验证
        /*UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginQuery.getUsername(), loginQuery.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);*/

        // 暂时这里自己处理账号密码校验逻辑，方便以后添加手机号短信验证码登录等其他登录方式
        // 通过用户名查询用户信息
        SysUserEntity user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUserEntity>()
                .eq(SysUserEntity::getUsername, loginQuery.getUsername()));
        // 找不到用户
        if (user == null) {
            throw new BadRequestException(ErrorCode.UNAUTHENTICATED, "用户名不存在或密码错误");
        }
        // 账号状态
        if (user.getState() == null || !user.getState().equals(Dict.UserState.OK.getCode())) {
            throw new BadRequestException(ErrorCode.PERMISSION_DENIED, "账号异常已被锁定，请联系系统管理员");
        }
        // 校验密码
        if (!new BCryptPasswordEncoder().matches(loginQuery.getPassword(), user.getPassword())) {
            // todo redis下各种高并发、集群、脏读等问题，欢迎各路大神pr，这里只是一个小功能就别介了
            // 密码错误，累计错误次数
            passwordRetryCount++;
            if (passwordRetryCount >= MAX_PASSWORD_RETRY_COUNT) {
                // 5分钟内连续输错5次密码，锁定账号30分钟
                redisUtils.set(retryKey, passwordRetryCount, 30, TimeUnit.MINUTES);
            } else {
                redisUtils.set(retryKey, passwordRetryCount, 5, TimeUnit.MINUTES);
            }
            throw new BadRequestException(ErrorCode.UNAUTHENTICATED,
                    "用户名不存在或密码错误，密码错误次数：" + passwordRetryCount);
        }

        /// 暂时停用，生成spring-security认证信息
        /*SecurityUser securityUser = new SecurityUser(user.getId(), user.getUsername(),
                user.getPassword(), Collections.emptyList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser,
                null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);*/

        // 登录成功，生成jwt
        String token = jwtUtils.createToken(user.getId());

        // 保存权限到redis
        List<ListAuthorityDTO> authorityList = sysUserMapper.listAuthoritiesByUserId(user.getId());
        if (authorityList != null && authorityList.size() > 0) {
            Set<String> authoritySet = new HashSet<>();
            for (ListAuthorityDTO authority : authorityList) {
                authoritySet.add("ROLE_" + authority.getRoleName());
                authoritySet.add(authority.getAuthority());
            }
            String authorities = StringUtils.arrayToCommaDelimitedString(authoritySet.toArray());
            redisUtils.set("user:" + user.getId() + ":authorities",
                    authorities, jwtExpire, TimeUnit.SECONDS);
        }

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
