package com.izneus.bonfire.module.dingtalk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.request.OapiV2UserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetuserinfoResponse;
import com.izneus.bonfire.common.constant.ErrorCode;
import com.izneus.bonfire.common.exception.BadRequestException;
import com.izneus.bonfire.common.util.DingTalkUtil;
import com.izneus.bonfire.common.util.RedisUtil;
import com.izneus.bonfire.config.DingTalkAppConfig;
import com.izneus.bonfire.module.dingtalk.controller.v1.vo.GetSignVO;
import com.izneus.bonfire.module.dingtalk.service.DingTalkService;
import com.izneus.bonfire.module.security.JwtUtil;
import com.izneus.bonfire.module.system.controller.v1.vo.LoginVO;
import com.izneus.bonfire.module.system.entity.SysUserEntity;
import com.izneus.bonfire.module.system.mapper.SysUserMapper;
import com.izneus.bonfire.module.system.service.SysUserService;
import com.izneus.bonfire.module.system.service.dto.ListPrivDTO;
import com.taobao.api.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Izneus
 * @date 2022-12-15
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DingTalkServiceImpl implements DingTalkService {

    private final SysUserService userService;
    private final RedisUtil redisUtil;
    private final JwtUtil jwtUtil;

    @Value("${jwt.expire}")
    private Long jwtExpire;

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
        OapiGettokenResponse res;
        try {
            res = client.execute(request);
        } catch (ApiException e) {
            // 请求执行异常抛错
            throw new BadRequestException(ErrorCode.INTERNAL, "钉钉AccessToken请求异常", e);
        }
        if (res != null && res.isSuccess()) {
            // 取token，缓存在redis，并且返回
            accessToken = res.getAccessToken();
            redisUtil.set(appConfig.getAccessTokenKey(), accessToken, 115L, TimeUnit.MINUTES);
            return accessToken;
        } else {
            // 请求成功，返回结果里errcode标记失败
            log.error("获取钉钉accessToken失败: {}", res);
            throw new BadRequestException(ErrorCode.INTERNAL,
                    res != null ? res.getErrmsg() : "钉钉请求gettoken未知错误");
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
        OapiGetJsapiTicketResponse res;
        try {
            res = client.execute(req, getAccessToken(appConfig));
        } catch (ApiException e) {
            throw new BadRequestException(ErrorCode.INTERNAL, "钉钉JsapiTicket请求异常", e);
        }
        if (res != null && res.isSuccess()) {
            ticket = res.getTicket();
            redisUtil.set(appConfig.getJsapiTicketKey(), ticket, 115L, TimeUnit.MINUTES);
            return ticket;
        } else {
            log.error("获取钉钉jsapiTicket失败: {}", res);
            throw new BadRequestException(ErrorCode.INTERNAL,
                    res != null ? res.getErrmsg() : "钉钉请求get_jsapi_ticket未知错误");
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

    @Override
    public LoginVO ddLogin(String code, DingTalkAppConfig appConfig) {
        String accessToken = getAccessToken(appConfig);

        // todo 以后可以优化抽取这些钉钉请求代码
        // 先根据前端发来的code获取钉钉用户基础信息
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getuserinfo");
        OapiV2UserGetuserinfoRequest req = new OapiV2UserGetuserinfoRequest();
        req.setCode(code);
        OapiV2UserGetuserinfoResponse userInfoRes;
        try {
            userInfoRes = client.execute(req, accessToken);
        } catch (ApiException e) {
            throw new BadRequestException(ErrorCode.INTERNAL, "免登阶段钉钉user/getuserinfo请求异常", e);
        }
        if (userInfoRes == null || !userInfoRes.isSuccess()) {
            log.error("免登阶段钉钉user/getuserinfo失败: {}", userInfoRes);
            throw new BadRequestException(ErrorCode.INTERNAL,
                    userInfoRes != null ? userInfoRes.getErrmsg() : "免登阶段钉钉请求user/getuserinfo未知错误");
        }
        String ddUserId = userInfoRes.getResult().getUserid();

        // 获取钉钉用户详情
        client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/get");
        OapiV2UserGetRequest userGetRequest = new OapiV2UserGetRequest();
        userGetRequest.setUserid(ddUserId);
        userGetRequest.setLanguage("zh_CN");
        OapiV2UserGetResponse userGetRes;
        try {
            userGetRes = client.execute(userGetRequest, accessToken);
        } catch (ApiException e) {
            throw new BadRequestException(ErrorCode.INTERNAL, "免登阶段钉钉user/get请求异常", e);
        }
        if (userGetRes == null || !userGetRes.isSuccess()) {
            log.error("免登阶段钉钉user/get失败: {}", userGetRes);
            throw new BadRequestException(ErrorCode.INTERNAL,
                    userGetRes != null ? userGetRes.getErrmsg() : "免登阶段钉钉user/get未知错误");
        }

        // todo 演示逻辑代码，可根据实际情况修改
        // 判断登录条件，比如使用钉钉的手机号码，和本地系统的手机号码匹配直接登录
        String mobile = userGetRes.getResult().getMobile();
        SysUserEntity userEntity = userService.getOne(new LambdaQueryWrapper<SysUserEntity>()
                .eq(SysUserEntity::getMobile, mobile));
        String sysUserId;
        if (userEntity == null) {
            // 手机号不存在，写用户表
            SysUserEntity e = new SysUserEntity();
            e.setMobile(mobile);
            e.setUsername(mobile);
            e.setFullname(userGetRes.getResult().getName());
            e.setStatus("0");
            userService.save(e);
            sysUserId = e.getId();
        } else {
            // 手机号存在，直接用该用户登录
            sysUserId = userEntity.getId();
        }

        // 登录后逻辑和LoginService的正常登录一样
        // 登录成功，生成jwt
        String token = jwtUtil.createToken(sysUserId);
        // 保存权限到redis
        List<ListPrivDTO> privList = ((SysUserMapper) userService.getBaseMapper()).listPrivsByUserId(sysUserId);
        if (privList != null && privList.size() > 0) {
            // 取角色和权限，拼接成一个字符串，用半角逗号分隔
            String roles = privList.stream().map(i -> "ROLE_" + i.getRoleName()).distinct()
                    .collect(Collectors.joining(","));
            String privs = privList.stream().map(ListPrivDTO::getPrivKey).distinct()
                    .collect(Collectors.joining(","));
            String key = redisUtil.getPrivilegeKey(sysUserId);
            redisUtil.set(key, roles + "," + privs, jwtExpire, TimeUnit.SECONDS);
        }
        log.info("token: {}", token);
        return LoginVO.builder().token(token).build();
    }

}
