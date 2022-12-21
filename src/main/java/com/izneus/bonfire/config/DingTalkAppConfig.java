package com.izneus.bonfire.config;

import lombok.Data;

/**
 * 钉钉单个应用参数设置，相关参数需要在钉钉开放平台建立应用之后才有
 * 这里默认指的是企业内部自建应用
 *
 * @author Izneus
 * @date 2022-12-15
 */
@Data
public class DingTalkAppConfig {

    /**
     * 钉钉组织id
     */
    private String corpId;

    /**
     * 钉钉应用参数
     */
    private Long agentId;

    /**
     * 钉钉应用参数
     */
    private String appKey;

    /**
     * 钉钉应用参数
     */
    private String appSecret;

    /**
     * 调用某些钉钉API，需要调用者的userId，一般就设置成子管理员这些权限较高的人
     */
    private String adminUserId;

    /**
     * accessToken缓存在redis时候用的key，注意要唯一，否则互相冲突
     */
    private String accessTokenKey;

    /**
     * jsTicket缓存在redis时候用的key，注意要唯一，否则互相冲突
     */
    private String jsapiTicketKey;

}
