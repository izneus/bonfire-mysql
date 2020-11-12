package com.izneus.bonfire.common.exception;

import com.izneus.bonfire.common.constant.ErrorCode;
import lombok.Getter;

/**
 * 通用异常
 *
 * @author Izneus
 * @date 2020/07/02
 */
@Getter
public class BadRequestException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String errorMessage;

    /*public BadRequestException(String errorMessage) {
        super(errorMessage);
        this.errorCode = ErrorCode.INVALID_ARGUMENT;
        this.errorMessage = errorMessage;
    }*/

    public BadRequestException(ErrorCode errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BadRequestException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = ErrorCode.INVALID_ARGUMENT;
        this.errorMessage = errorMessage;
    }

    public BadRequestException(ErrorCode errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
