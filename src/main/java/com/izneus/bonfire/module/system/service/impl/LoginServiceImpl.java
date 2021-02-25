package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.izneus.bonfire.common.constant.Dict;
import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.BadRequestException;
import com.izneus.bonfire.common.util.RedisUtil;
import com.izneus.bonfire.config.BonfireConfig;
import com.izneus.bonfire.module.security.JwtUtil;
import com.izneus.bonfire.module.system.controller.v1.query.LoginQuery;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.mapper.SysUserMapper;
import com.izneus.bonfire.module.system.service.LoginService;
import com.izneus.bonfire.module.system.controller.v1.vo.CaptchaVO;
import com.izneus.bonfire.module.system.service.dto.ListAuthDTO;
import com.izneus.bonfire.module.system.controller.v1.vo.LoginVO;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.izneus.bonfire.common.constant.Constant.*;

/**
 * @author Izneus
 * @date 2020/07/01
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final SysUserMapper sysUserMapper;
    private final BonfireConfig bonfireConfig;

    @Value("${jwt.expire}")
    private Long jwtExpire;

    @Override
    public LoginVO login(LoginQuery loginQuery) {
        // 登陆密码重试锁定功能, 检查连续密码输入错误次数
        String retryKey = REDIS_KEY_LOGIN_RETRY + loginQuery.getUsername();
        int retryCount = (int) Optional.ofNullable(redisUtil.get(retryKey)).orElse(0);
        if (retryCount >= MAX_RETRY_COUNT) {
            throw new BadRequestException(ErrorCode.PERMISSION_DENIED,
                    "因连续密码输入错误，该用户已被锁定，请30分钟之后重试");
        }

        if (bonfireConfig.getCaptchaEnabled()) {
            // 查询验证码
            String key = REDIS_KEY_CAPTCHA + loginQuery.getCaptchaId();
            String captcha = (String) redisUtil.get(key);
            // 查询过的验证码及时清除
            redisUtil.del(key);
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
        if (user.getStatus() == null || !user.getStatus().equals(Dict.UserStatus.OK.getCode())) {
            throw new BadRequestException(ErrorCode.PERMISSION_DENIED, "账号异常已被锁定，请联系系统管理员");
        }
        // 校验密码
        if (!new BCryptPasswordEncoder().matches(loginQuery.getPassword(), user.getPassword())) {
            // 密码错误，累计错误次数
            Long totalRetryCount = redisUtil.incr(retryKey);
            if (totalRetryCount >= MAX_RETRY_COUNT) {
                // 5分钟内连续输错5次密码，锁定账号30分钟
                redisUtil.expire(retryKey, 30, TimeUnit.MINUTES);
            } else {
                redisUtil.expire(retryKey, 5, TimeUnit.MINUTES);
            }
            throw new BadRequestException(ErrorCode.UNAUTHENTICATED,
                    "用户名不存在或密码错误，密码错误次数：" + totalRetryCount);
        }

        /// 暂时停用，生成spring-security认证信息
        /*SecurityUser securityUser = new SecurityUser(user.getId(), user.getUsername(),
                user.getPassword(), Collections.emptyList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser,
                null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);*/

        // 登录成功，生成jwt
        String token = jwtUtil.createToken(user.getId());

        // 保存权限到redis
        List<ListAuthDTO> authorityList = sysUserMapper.listAuthoritiesByUserId(user.getId());
        if (authorityList != null && authorityList.size() > 0) {
            Set<String> authoritySet = new HashSet<>();
            for (ListAuthDTO authority : authorityList) {
                authoritySet.add("ROLE_" + authority.getRoleName());
                authoritySet.add(authority.getAuthority());
            }
            String authorities = StringUtils.arrayToCommaDelimitedString(authoritySet.toArray());
            redisUtil.set("user:" + user.getId() + ":authorities",
                    authorities, jwtExpire, TimeUnit.SECONDS);
        }

        // todo single login

        return new LoginVO(user.getUsername(), token);
    }

    @Override
    public CaptchaVO getCaptcha() {
        /// 图片验证码
        /*SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String value = specCaptcha.text().toLowerCase();*/

        // 算术验证码
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        String value = captcha.text();

        String uuid = IdUtil.fastSimpleUUID();
        String key = REDIS_KEY_CAPTCHA + uuid;
        // 保存验证码到redis缓存，2分钟后过期
        redisUtil.set(key, value, 2L, TimeUnit.MINUTES);
        return new CaptchaVO(uuid, captcha.toBase64());
    }
}
