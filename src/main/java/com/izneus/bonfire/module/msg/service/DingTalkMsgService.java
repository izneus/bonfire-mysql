package com.izneus.bonfire.module.msg.service;

/**
 * 钉钉消息发送
 *
 * @author Izneus
 * @date 2022-07-29
 */
public interface DingTalkMsgService {

    String getAccessToken();

    String send();
}
