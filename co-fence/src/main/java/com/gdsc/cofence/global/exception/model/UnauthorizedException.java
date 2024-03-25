package com.gdsc.cofence.global.exception.model;

import com.gdsc.cofence.global.exception.ErrorCode;

public class UnauthorizedException extends CustomException{

    public UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
