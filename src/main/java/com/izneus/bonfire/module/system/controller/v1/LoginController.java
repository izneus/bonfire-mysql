package com.izneus.bonfire.module.system.controller.v1;

import com.izneus.bonfire.module.system.controller.v1.query.LoginQuery;
import com.izneus.bonfire.module.system.service.LoginService;
import com.izneus.bonfire.module.system.service.dto.LoginDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 *
 * @author Izneus
 * @date 2020/06/28
 */
@Api(tags = "登录")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService loginService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public LoginDTO login(@Validated @RequestBody LoginQuery loginQuery) {
        return loginService.login(loginQuery);
    }

    @PostMapping("/logout")
    public String logout() {
        return null;
    }
}
