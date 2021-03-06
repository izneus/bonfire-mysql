package com.izneus.bonfire.module.system.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.izneus.bonfire.common.constant.Dict;
import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.BadRequestException;
import com.izneus.bonfire.common.util.RedisUtil;
import com.izneus.bonfire.config.BonfireConfig;
import com.izneus.bonfire.module.security.JwtUtil;
import com.izneus.bonfire.module.system.controller.v1.query.LoginQuery;
import com.izneus.bonfire.module.system.controller.v1.vo.CacheDictVO;
import com.izneus.bonfire.module.system.controller.v1.vo.CaptchaVO;
import com.izneus.bonfire.module.system.controller.v1.vo.LoginVO;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.mapper.SysUserMapper;
import com.izneus.bonfire.module.system.service.LoginService;
import com.izneus.bonfire.module.system.service.SysUserService;
import com.izneus.bonfire.module.system.service.dto.ListAuthDTO;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.izneus.bonfire.common.constant.Constant.*;

/**
 * @author Izneus
 * @date 2020/07/01
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final BonfireConfig bonfireConfig;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final SysUserService userService;

    @Value("${jwt.expire}")
    private Long jwtExpire;

    @Override
    public LoginVO login(LoginQuery loginQuery) {
        // 登陆密码重试锁定功能, 检查连续密码输入错误次数
        String retryKey = StrUtil.format(REDIS_KEY_LOGIN_RETRY, loginQuery.getUsername());
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
        SysUserEntity user = userService.getOne(new LambdaQueryWrapper<SysUserEntity>()
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
            if (0 == retryCount) {
                // 首次输错密码，开始计时5分钟
                redisUtil.set(retryKey, 0, 5, TimeUnit.MINUTES);
            }
            // 密码错误，累计错误次数
            Long totalRetryCount = redisUtil.incr(retryKey);
            if (totalRetryCount >= MAX_RETRY_COUNT) {
                // 5分钟内连续输错5次密码，锁定账号30分钟
                redisUtil.expire(retryKey, 30, TimeUnit.MINUTES);
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
        List<ListAuthDTO> authorityList = ((SysUserMapper) userService.getBaseMapper()).listAuthsByUserId(user.getId());
        if (authorityList != null && authorityList.size() > 0) {
            // 取角色和权限，拼接成一个字符串，用半角逗号分隔
            String roles = authorityList.stream().map(i -> "ROLE_" + i.getRoleName()).distinct()
                    .collect(Collectors.joining(","));
            String auths = authorityList.stream().map(ListAuthDTO::getAuthority).distinct()
                    .collect(Collectors.joining(","));
            String key = StrUtil.format(REDIS_KEY_AUTHS, user.getId());
            redisUtil.set(key, roles + "," + auths, jwtExpire, TimeUnit.SECONDS);
        }
        // todo single login
        return LoginVO.builder()
                .username(user.getUsername())
                .token(token)
                .build();
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
        return CaptchaVO.builder()
                .id(uuid)
                .captcha(captcha.toBase64())
                .build();
    }
}
