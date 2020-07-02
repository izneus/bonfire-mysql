package com.izneus.bonfire.common.exception;

import com.izneus.bonfire.common.constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局出错处理
 *
 * @author Izneus
 * @date 2020/06/29
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException e) {
        log.error("BadRequestException", e);
        // 提取错误码的前三位作为HttpStatusCode
        String value = String.valueOf(e.getErrorCode().getValue());
        String httpStatusCode = value.substring(0, 3);
        // 构造返回
        return new ResponseEntity<>(
                new ApiError(e.getErrorCode(), e.getErrorMessage(), e.getMessage()),
                HttpStatus.valueOf(Integer.parseInt(httpStatusCode))
        );
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 打印堆栈信息
        log.error("MethodArgumentNotValidException", e);
        // 拼装错误返回信息
        StringBuilder message = new StringBuilder();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            message.append(error.getDefaultMessage()).append("。");
        }
        return new ApiError(ErrorCode.INVALID_ARGUMENT, message.toString(), e.getMessage());
    }

    /**
     * 处理所有其他异常
     */
    @ExceptionHandler(Exception.class)
    public ApiError handleException(Exception e) {
        // 打印堆栈信息
        log.error("Exception", e);
        return new ApiError(ErrorCode.INTERNAL, "服务器内部错误，请联系管理员", e.getMessage());
    }
}
