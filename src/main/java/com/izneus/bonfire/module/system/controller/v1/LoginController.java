package com.izneus.bonfire.module.system.controller.v1;

import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.module.system.controller.v1.query.LoginQuery;
import com.izneus.bonfire.module.system.service.LoginService;
import com.izneus.bonfire.module.system.service.dto.CaptchaDTO;
import com.izneus.bonfire.module.system.service.dto.LoginDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 登录，当前默认用户名admin1，默认密码Admin123
 *
 * @author Izneus
 * @date 2020/06/28
 */
@Api(tags = "系统:登录")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService loginService;

    @ApiOperation("获得验证码")
    @GetMapping("/captcha")
    public CaptchaDTO getCaptcha() {
        return loginService.getCaptcha();
    }

    @ApiOperation("用户登录")
    @AccessLog("用户登录")
    @PostMapping("/login")
    public LoginDTO login(@Validated @RequestBody LoginQuery loginQuery) {
        return loginService.login(loginQuery);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public String logout() {
        return null;
    }
}
