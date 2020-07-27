package com.izneus.bonfire.module.system.service;

import com.izneus.bonfire.module.system.controller.v1.query.LoginQuery;
import com.izneus.bonfire.module.system.service.dto.CaptchaDTO;
import com.izneus.bonfire.module.system.service.dto.LoginDTO;

/**
 * @author Izneus
 * @date 2020/07/17
 */
public interface LoginService {
    /**
     * 登录
     *
     * @param loginQuery login表单
     * @return LoginDTO
     */
    LoginDTO login(LoginQuery loginQuery);

    /**
     * 生成登录用的验证码，缓存在redis里等待校验
     *
     * @return CaptchaDTO
     */
    CaptchaDTO getCaptcha();
}
