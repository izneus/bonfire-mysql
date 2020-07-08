package com.izneus.bonfire.module.system.controller.v1;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统_用户 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2020-06-28
 */
@Api(tags = "系统：用户")
@RestController
@RequestMapping("/api/v1/users")
public class SysUserController {

    @ApiOperation("aaa")
    @GetMapping("aaa")
    public String listUsers() {
        return "aaa";
    }


}
