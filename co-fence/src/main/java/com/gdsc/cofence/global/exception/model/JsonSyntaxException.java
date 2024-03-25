package com.gdsc.cofence.global.exception.model;

import com.gdsc.cofence.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class JsonSyntaxException extends CustomException{

    public JsonSyntaxException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
