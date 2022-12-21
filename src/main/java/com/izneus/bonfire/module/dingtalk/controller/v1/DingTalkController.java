package com.izneus.bonfire.module.dingtalk.controller.v1;

import com.izneus.bonfire.common.annotation.AccessLog;
import com.izneus.bonfire.config.DingTalkConfig;
import com.izneus.bonfire.module.dingtalk.controller.v1.query.GetSignQuery;
import com.izneus.bonfire.module.dingtalk.controller.v1.vo.GetSignVO;
import com.izneus.bonfire.module.dingtalk.service.DingTalkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Izneus
 * @date 2022-12-15
 */
@Api(tags = "钉钉:默认")
@RequestMapping("/api/v1/dd")
@RequiredArgsConstructor
@RestController
public class DingTalkController {

    private final DingTalkService dingTalkService;
    private final DingTalkConfig dingTalkConfig;

    /*@AccessLog("测试钉钉")
    @ApiOperation("测试钉钉")
    @PostMapping("/test")
    public String test() {
        return dingTalkService.getAccessToken(dingTalkConfig.getAppBonfire());
    }*/

    @AccessLog("获取钉钉JSAPI鉴权")
    @ApiOperation("获取钉钉JSAPI鉴权")
    @PostMapping("/bonfire/getSign")
    public GetSignVO bfGetSign(@Validated @RequestBody GetSignQuery query) {
        return dingTalkService.getSign(dingTalkConfig.getAppBonfire(), query.getUrl());
    }

}
