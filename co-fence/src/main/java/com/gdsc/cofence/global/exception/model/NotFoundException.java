package com.gdsc.cofence.global.exception.model;

import com.gdsc.cofence.global.exception.ErrorCode;


public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}