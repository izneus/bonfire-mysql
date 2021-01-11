package com.izneus.bonfire.module.security;

import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 获取当前用户的信息
 *
 * @author Izneus
 * @date 2020/01/08
 */
public class CurrentUserUtil {

    /**
     * 获取当前用户
     *
     * @return 系统用户名称
     */
    public static SecurityUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(ErrorCode.UNAUTHENTICATED, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            return (SecurityUser) authentication.getPrincipal();
        }
        throw new BadRequestException(ErrorCode.UNAUTHENTICATED, "找不到当前登录的信息");
    }

    /**
     * 判断当前用户是否有某个角色
     *
     * @param roleName 角色名称
     * @return boolean
     */
    public static boolean hasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

    /**
     * 获取当前用户的id
     *
     * @return userId
     */
    public static String getUserId() {
        return getUser().getId();
    }

}
