package com.gdsc.cofence.exception.model;

import com.gdsc.cofence.exception.ErrorCode;

public class UnauthorizedException extends CustomException{

    public UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
