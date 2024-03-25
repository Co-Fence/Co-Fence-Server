package com.gdsc.cofence.global.exception.model;

import com.gdsc.cofence.global.exception.ErrorCode;

public class BadRequestException extends CustomException {
    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
