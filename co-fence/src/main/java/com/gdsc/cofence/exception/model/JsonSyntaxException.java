package com.gdsc.cofence.exception.model;

import com.gdsc.cofence.exception.ErrorCode;
import lombok.Getter;

@Getter
public class JsonSyntaxException extends CustomException{

    public JsonSyntaxException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
