package com.gdsc.cofence.exception.model;

import com.gdsc.cofence.exception.ErrorCode;


public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}