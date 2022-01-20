package com.izneus.bonfire.module.system.controller.v1;

import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.module.system.controller.v1.vo.PrivTreeVO;
import com.izneus.bonfire.module.system.service.SysPrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统_权限 前端控制器
 * </p>
 *
 * @author Izneus
 * @since 2022-01-19
 */
@Api(tags = "系统:权限")
@RestController
@RequestMapping("/api/v1/privilege")
@RequiredArgsConstructor
public class SysPrivilegeController {

    private final SysPrivilegeService privilegeService;

    @AccessLog("权限树")
    @ApiOperation("权限树")
    @PostMapping("/getPrivilegeTree")
    @PreAuthorize("hasAuthority('sys:priv:list') or hasAuthority('admin')")
    public List<PrivTreeVO> getPrivilegeTree() {
        return privilegeService.getPrivilegeTree();

    }

}
