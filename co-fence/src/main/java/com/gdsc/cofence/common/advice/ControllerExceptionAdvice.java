package com.gdsc.cofence.common.advice;


import com.gdsc.cofence.common.dto.ApiResponse;
import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Component
@RequiredArgsConstructor
@ControllerAdvice
public class ControllerExceptionAdvice {

    // 400 BAD_REQUEST
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        return ApiResponse.error(ErrorCode.VALIDATION_REQUEST_MISSING_EXCEPTION,
                String.format("%s. (%s)", fieldError.getDefaultMessage(), fieldError.getField()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ApiResponse handleMissingRequestHeaderException(final MissingRequestHeaderException e) {
        return ApiResponse.error(ErrorCode.VALIDATION_REQUEST_HEADER_MISSING_EXCEPTION,
                String.format("%s. (%s)", ErrorCode.VALIDATION_REQUEST_HEADER_MISSING_EXCEPTION.getMessage(), e.getHeaderName()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ApiResponse handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        return ApiResponse.error(ErrorCode.VALIDATION_REQUEST_PARAMETER_MISSING_EXCEPTION,
                String.format("%s. (%s)", ErrorCode.VALIDATION_REQUEST_PARAMETER_MISSING_EXCEPTION.getMessage(), e.getParameterName()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ApiResponse handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        return ApiResponse.error(ErrorCode.REQUEST_METHOD_VALIDATION_EXCEPTION, e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    protected ApiResponse handleAccessDeniedException(final AccessDeniedException e) {
        return ApiResponse.error(ErrorCode.ACCESS_DENIED_EXCEPTION, e.getMessage());
    }

    // 500 Internal Server Error
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ApiResponse<Object> handleException() {
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
    }

    // CustomError
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ApiResponse> handleGdscException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiResponse.error(e.getErrorCode(), e.getMessage()));
    }
}
