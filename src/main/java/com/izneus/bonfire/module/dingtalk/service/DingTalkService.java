package com.izneus.bonfire.module.dingtalk.service;

import com.izneus.bonfire.config.DingTalkAppConfig;
import com.izneus.bonfire.module.dingtalk.controller.v1.vo.GetSignVO;
import com.izneus.bonfire.module.system.controller.v1.vo.LoginVO;

/**
 * @author Izneus
 * @date 2022-12-15
 */
public interface DingTalkService {
    /**
     * 获取并且缓存钉钉accessToken
     *
     * @param appConfig 钉钉应用参数
     * @return accessToken
     */
    String getAccessToken(DingTalkAppConfig appConfig);

    /**
     * 获取并且缓存钉钉jsapiTicket
     *
     * @param appConfig 钉钉应用参数
     * @return jsapiTicket
     */
    String getJsapiTicket(DingTalkAppConfig appConfig);

    /**
     * 获取前端js鉴权所需要的参数
     *
     * @param appConfig 钉钉应用参数
     * @param url       前端在js鉴权的访问地址
     * @return 前端js鉴权所需要的参数
     */
    GetSignVO getSign(DingTalkAppConfig appConfig, String url);

    /**
     * 钉钉免登
     *
     * @param code      前端传递的临时授权码
     * @param appConfig 应用参数
     * @return 登录vo
     */
    LoginVO ddLogin(String code, DingTalkAppConfig appConfig);

}
