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
     * 无错误，返回成功
     * HTTP Mapping: 200 OK
     */
    OK(20000, "请求成功"),

    /**
     * 操作已取消
     * HTTP Mapping: 499 Client Closed Request
     */
    CANCELLED(49901, "已取消"),

    /**
     * 出现未知的服务器错误，通常是服务器错误
     * HTTP Mapping: 500 Internal Server Error
     */
    UNKNOWN(50002, "未知错误"),

    /**
     * 客户端指定了无效参数
     * HTTP Mapping: 400 Bad Request
     */
    INVALID_ARGUMENT(40003, "非法参数"),

    /**
     * 超出请求时限
     * HTTP Mapping: 504 Gateway Timeout
     */
    DEADLINE_EXCEEDED(50404, "已超时"),

    /**
     * 找不到指定的资源，或者请求由于未公开的原因（例如白名单）而被拒绝
     * HTTP Mapping: 404 Not Found
     */
    NOT_FOUND(40405, "无指定资源"),

    /**
     * 客户端尝试创建的资源已存在
     * HTTP Mapping: 409 Conflict
     */
    ALREADY_EXISTS(40906, "已存在"),

    /**
     * 客户端权限不足
     * HTTP Mapping: 403 Forbidden
     */
    PERMISSION_DENIED(40307, "无权限"),

    /**
     * 资源配额不足或达到速率限制
     * HTTP Mapping: 429 Too Many Requests
     */
    RESOURCE_EXHAUSTED(42908, "资源不足"),

    /**
     * 请求无法在当前系统状态下执行，例如删除非空目录
     * HTTP Mapping: 400 Bad Request
     */
    FAILED_PRECONDITION(40009, "无法执行"),

    /**
     * 操作终止，冲突，例如读取/修改/写入冲突
     * HTTP Mapping: 409 Conflict
     */
    ABORTED(40910, "操作冲突"),

    /**
     * 客户端指定了无效范围
     * HTTP Mapping: 400 Bad Request
     */
    OUT_OF_RANGE(40011, "无效范围"),

    /**
     * API 方法未通过服务器实现
     * HTTP Mapping: 501 Not Implemented
     */
    UNIMPLEMENTED(50112, "未实现"),

    /**
     * 出现内部服务器错误，通常是服务器错误
     * HTTP Mapping: 500 Internal Server Error
     */
    INTERNAL(50013, "服务器内部错误"),

    /**
     * 服务不可用，通常是服务器已关闭
     * HTTP Mapping: 503 Service Unavailable
     */
    UNAVAILABLE(50314, "服务不可用"),

    /**
     * 出现不可恢复的数据丢失或数据损坏
     * HTTP Mapping: 500 Internal Server Error
     */
    DATA_LOSS(50015, "数据丢失"),

    /**
     * 由于 OAuth 令牌丢失、无效或过期，请求未通过身份验证
     * HTTP Mapping: 401 Unauthorized
     */
    UNAUTHENTICATED(40116, "未认证");

    private final int value;
    private final String reason;
}
