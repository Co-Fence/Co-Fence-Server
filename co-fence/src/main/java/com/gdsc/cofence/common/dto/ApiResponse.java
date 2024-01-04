package com.gdsc.cofence.common.dto;

import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.SuccessCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final int code;
    private final String message;
    private T data;

    public static ApiResponse success(SuccessCode successCode) {
        return new ApiResponse<>(successCode.getHttpStatusCode(), successCode.getMessage());
    }

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return new ApiResponse<T>(successCode.getHttpStatusCode(), successCode.getMessage(), data);
    }

    public static ApiResponse error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getHttpStatusCode(), errorCode.getMessage());
    }

    public static ApiResponse error(ErrorCode errorCode, String message) {
        return new ApiResponse(errorCode.getHttpStatusCode(), errorCode.getMessage(), message);
    }
}

