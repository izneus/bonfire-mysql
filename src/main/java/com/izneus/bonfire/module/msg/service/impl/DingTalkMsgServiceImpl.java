package com.izneus.bonfire.module.msg.service.impl;

import cn.hutool.core.date.DateUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.izneus.bonfire.common.constant.Constant;
import com.izneus.bonfire.common.util.RedisUtil;
import com.izneus.bonfire.module.msg.service.DingTalkMsgService;
import com.taobao.api.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 当前为企业内部应用类型，具体可参见钉钉开放平台文档消息通知部分：
 * https://open.dingtalk.com/document/orgapp-server/message-notification-overview
 *
 * @author Izneus
 * @date 2022-07-29
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DingTalkMsgServiceImpl implements DingTalkMsgService {

    @Value("${dingtalk.agentId}")
    private Long agentId;

    @Value("${dingtalk.appKey}")
    private String appKey;

    @Value("${dingtalk.appSecret}")
    private String appSecret;

    @Value("${dingtalk.adminUserId}")
    private String adminUserId;

    private final RedisUtil redisUtil;

    @Override
    public String getAccessToken() {
        // 先从redis缓存获得
        String accessToken = (String) redisUtil.get(Constant.RedisKey.DINGTALK_ACCESS_TOKEN);
        if (accessToken != null) {
            return accessToken;
        }
        // redis里没有，重新请求钉钉api获得
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appKey);
        request.setAppsecret(appSecret);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = null;
        try {
            response = client.execute(request);
        } catch (ApiException e) {
            log.error("请求钉钉AccessToken失败", e);
        }
        if (response == null) {
            return null;
        }
        if (response.getErrcode().equals(0L)) {
            // 取token，缓存在redis，并且返回
            accessToken = response.getAccessToken();
            redisUtil.set(Constant.RedisKey.DINGTALK_ACCESS_TOKEN, accessToken, 115L, TimeUnit.MINUTES);
            return accessToken;
        } else {
            return null;
        }
    }

    @Override
    public String send() {
        // todo 钉钉发送消息还需要优化代码，目前钉钉部分代码都只是测试
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(agentId);
        // 用管理员id测试一下
        request.setUseridList(adminUserId);
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent("测试钉钉工作通知，当前时间为：" + DateUtil.now());
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response rsp = null;
        try {
            rsp = client.execute(request, getAccessToken());
        } catch (ApiException e) {
            log.error("发送钉钉文本信息失败", e);
        }
        if (rsp == null) {
            return null;
        }
        log.info(rsp.getBody());
        return rsp.getRequestId();
    }

}
