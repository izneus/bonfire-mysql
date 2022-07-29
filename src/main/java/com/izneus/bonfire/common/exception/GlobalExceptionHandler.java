package com.izneus.bonfire.common.exception;

import com.izneus.bonfire.common.constant.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
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
 * @date 2020-06-29
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理自定义的错误请求类异常
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException e) {
        log.error("BadRequestException", e);
        // 提取错误码的前三位作为HttpStatusCode
        String value = String.valueOf(e.getErrorCode().getValue());
        String httpStatusCode = value.substring(0, 3);
        // 构造返回
        return new ResponseEntity<>(
                new ApiError(e.getErrorCode(), e.getErrorMessage(), e.toString()),
                HttpStatus.valueOf(Integer.parseInt(httpStatusCode))
        );
    }

    /**
     * 主要是处理loadUserByUsername里抛出的UsernameNotFoundException，
     * 因为hideUserNotFoundExceptions=true会转为BadCredentialsException
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError badCredentialsException(BadCredentialsException e) {
        log.error("BadCredentialsException", e);
        return new ApiError(ErrorCode.UNAUTHENTICATED, e.getMessage(), e.toString());
    }

    /**
     * InternalAuthenticationServiceException
     * 主要是处理loadUserByUsername里抛出的自定义错误
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        log.error("InternalAuthenticationServiceException", e);
        return new ApiError(ErrorCode.UNAUTHENTICATED, e.getMessage(), e.toString());
    }

    /**
     * 处理所有接口数据验证异常
     */
    @SuppressWarnings("Duplicates")
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
        return new ApiError(ErrorCode.INVALID_ARGUMENT, message.toString(), e.toString());
    }

    /**
     * 处理父类参数校验异常，同上
     */
    @SuppressWarnings("Duplicates")
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBindException(BindException e) {
        // 打印堆栈信息
        log.error("BindException", e);
        // 拼装错误返回信息
        StringBuilder message = new StringBuilder();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            String[] codes = error.getCodes();
            message.append(codes != null ? codes[0] : null)
                    .append(error.getDefaultMessage())
                    .append("。");
        }
        return new ApiError(ErrorCode.INVALID_ARGUMENT, message.toString(), e.toString());
    }

    /**
     * 处理SpringSecurity无权限错误
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleAccessDeniedException(AccessDeniedException e) {
        // 打印堆栈信息
        log.error("AccessDeniedException", e);
        return new ApiError(ErrorCode.PERMISSION_DENIED, e.getMessage(), e.toString());
    }

    /**
     * 处理ExpiredJwtException无权限错误
     */
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleAccessDeniedException(ExpiredJwtException e) {
        // 打印堆栈信息
        log.error("ExpiredJwtException", e);
        return new ApiError(ErrorCode.PERMISSION_DENIED, "token已过期", e.toString());
    }

    /**
     * 处理所有其他异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(Exception e) {
        // 打印堆栈信息
        log.error("Exception", e);
        return new ApiError(ErrorCode.INTERNAL, "服务器内部错误，请联系管理员", e.toString());
    }
}
