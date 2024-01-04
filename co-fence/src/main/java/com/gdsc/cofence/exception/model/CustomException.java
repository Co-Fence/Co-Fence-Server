package com.gdsc.cofence.exception.model;

import com.gdsc.cofence.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getHttpStatus() {
        return errorCode.getHttpStatusCode();
    }
}
