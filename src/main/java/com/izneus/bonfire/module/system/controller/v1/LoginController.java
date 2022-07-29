package com.izneus.bonfire.module.system.controller.v1;

import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.module.system.controller.v1.query.LoginQuery;
import com.izneus.bonfire.module.system.service.LoginService;
import com.izneus.bonfire.module.system.controller.v1.vo.CaptchaVO;
import com.izneus.bonfire.module.system.controller.v1.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 登录，当前默认用户名admin1，默认密码Admin123
 *
 * @author Izneus
 * @date 2020-06-28
 */
@Api(tags = "系统:登录")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService loginService;

    @AccessLog("获得验证码")
    @ApiOperation("获得验证码")
    @GetMapping("/captcha")
    public CaptchaVO getCaptcha() {
        return loginService.getCaptcha();
    }

    @AccessLog("用户登录")
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public LoginVO login(@Validated @RequestBody LoginQuery loginQuery) {
        return loginService.login(loginQuery);
    }

    @AccessLog("登出")
    @ApiOperation("登出")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
        loginService.logout();
    }
}
