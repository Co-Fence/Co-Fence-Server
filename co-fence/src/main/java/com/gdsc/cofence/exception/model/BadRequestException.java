package com.gdsc.cofence.exception.model;

import com.gdsc.cofence.exception.ErrorCode;

public class BadRequestException extends CustomException {
    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
