package com.izneus.bonfire.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 错误码枚举
 *
 * @author Izneus
 * @date 2020/06/30
 */
@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    /**
     * 无错误
     */
    OK(20000, "请求成功"),

    /**
     * 客户端指定了无效参数
     */
    INVALID_ARGUMENT(40003, "非法参数"),

    /**
     * 找不到指定的资源，或者请求由于未公开的原因（例如白名单）而被拒绝
     */
    NOT_FOUND(40405, "无指定资源"),

    /**
     * 客户端尝试创建的资源已存在
     */
    ALREADY_EXISTS(40906, "已存在"),

    /**
     * 出现未知的服务器错误，通常是服务器错误
     */
    UNKNOWN(50002, "未知错误"),

    /**
     * 出现内部服务器错误，通常是服务器错误
     */
    INTERNAL(50013, "服务器内部错误");

    private final int value;
    private final String reason;
}
