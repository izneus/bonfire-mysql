package com.izneus.bonfire.common.exception;

import com.izneus.bonfire.common.constant.ErrorCode;
import lombok.Data;

/**
 * 统一异常处理返回信息
 *
 * @author Izneus
 * @date 2020/06/30
 */
@Data
public class ApiError {

    private int code;
    private String message;
    private String status;
    private String exception;
    // 可以按需添加自定义内容丰富错误返回，帮助debug
    // private List<ErrorDetail> details;

    public ApiError(ErrorCode errorCode, String message, String exception) {
        this.message = message;
        this.code = errorCode.getValue();
        this.status = errorCode.getReason();
        this.exception = exception;
    }
}
