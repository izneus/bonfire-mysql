package com.izneus.bonfire.module.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.izneus.bonfire.common.constant.Dict;
import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.BadRequestException;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * spring security使用的UserDetailsService实现
 *
 * @author Izneus
 * @date 2020/07/03
 */
@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名查询用户信息
        SysUserEntity user = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUserEntity>()
                .eq(SysUserEntity::getUsername, username));
        // 找不到用户
        if (user == null) {
            throw new BadRequestException(ErrorCode.INVALID_ARGUMENT, "用户名不存在或密码错误");
        }
        // 账号状态
        if (user.getState() == null || !user.getState().equals(Dict.UserState.OK.getCode())) {
            throw new BadRequestException(ErrorCode.PERMISSION_DENIED, "账号异常已被锁定，请联系系统管理员");
        }
        return new SecurityUser(user.getId(), user.getUsername(), user.getPassword(),
                AuthorityUtils.createAuthorityList("ADMIN"));
    }
}
