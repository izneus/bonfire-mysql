package com.izneus.bonfire.module.dingtalk.service.impl;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.BadRequestException;
import com.izneus.bonfire.common.util.DingTalkUtil;
import com.izneus.bonfire.common.util.RedisUtil;
import com.izneus.bonfire.config.DingTalkAppConfig;
import com.izneus.bonfire.module.dingtalk.controller.v1.vo.GetSignVO;
import com.izneus.bonfire.module.dingtalk.service.DingTalkService;
import com.taobao.api.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Izneus
 * @date 2022-12-15
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DingTalkServiceImpl implements DingTalkService {

    private static final Long DD_ERRCODE_OK = 0L;
    private final RedisUtil redisUtil;

    @Override
    public String getAccessToken(DingTalkAppConfig appConfig) {
        // 先从redis缓存获得
        String accessToken = (String) redisUtil.get(appConfig.getAccessTokenKey());
        if (accessToken != null) {
            return accessToken;
        }
        // redis里没有，重新请求钉钉api获得
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appConfig.getAppKey());
        request.setAppsecret(appConfig.getAppSecret());
        request.setHttpMethod("GET");
        OapiGettokenResponse response;
        try {
            response = client.execute(request);
        } catch (ApiException e) {
            // 请求执行异常抛错
            throw new BadRequestException(ErrorCode.INTERNAL, "钉钉AccessToken请求异常", e);
        }
        if (response != null && DD_ERRCODE_OK.equals(response.getErrcode())) {
            // 取token，缓存在redis，并且返回
            accessToken = response.getAccessToken();
            redisUtil.set(appConfig.getAccessTokenKey(), accessToken, 115L, TimeUnit.MINUTES);
            return accessToken;
        } else {
            // 请求成功，返回结果里errcode标记失败
            log.error("获取钉钉accessToken失败: {}", response);
            throw new BadRequestException(ErrorCode.INTERNAL,
                    response != null ? response.getErrmsg() : "钉钉请求gettoken未知错误");
        }
    }

    @Override
    public String getJsapiTicket(DingTalkAppConfig appConfig) {
        // 先从redis缓存获得
        String ticket = (String) redisUtil.get(appConfig.getJsapiTicketKey());
        if (ticket != null) {
            return ticket;
        }
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/get_jsapi_ticket");
        OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
        req.setHttpMethod("GET");
        OapiGetJsapiTicketResponse rsp;
        try {
            rsp = client.execute(req, getAccessToken(appConfig));
        } catch (ApiException e) {
            throw new BadRequestException(ErrorCode.INTERNAL, "钉钉JsapiTicket请求异常", e);
        }
        if (rsp != null && DD_ERRCODE_OK.equals(rsp.getErrcode())) {
            ticket = rsp.getTicket();
            redisUtil.set(appConfig.getJsapiTicketKey(), ticket, 115L, TimeUnit.MINUTES);
            return ticket;
        } else {
            log.error("获取钉钉jsapiTicket失败: {}", rsp);
            throw new BadRequestException(ErrorCode.INTERNAL,
                    rsp != null ? rsp.getErrmsg() : "钉钉请求get_jsapi_ticket未知错误");
        }
    }

    @Override
    public GetSignVO getSign(DingTalkAppConfig appConfig, String url) {
        String jsticket = getJsapiTicket(appConfig);
        String nonceStr = DingTalkUtil.getRandomStr(3);
        long timeStamp = System.currentTimeMillis();
        String sign;
        try {
            sign = DingTalkUtil.sign(jsticket, nonceStr, timeStamp, url);
        } catch (Exception e) {
            throw new BadRequestException(ErrorCode.INTERNAL, "生成钉钉js鉴权签名失败", e);
        }
        return GetSignVO.builder()
                .agentId(appConfig.getAgentId())
                .corpId(appConfig.getCorpId())
                .timeStamp(timeStamp)
                .nonceStr(nonceStr)
                .signature(sign)
                .build();
    }

}
