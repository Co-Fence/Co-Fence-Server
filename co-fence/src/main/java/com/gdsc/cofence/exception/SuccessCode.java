package com.gdsc.cofence.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {
    // 200 OK
    LOGIN_USER_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다"),
    GET_POST_SUCCESS(HttpStatus.OK, "게시글 조회가 완료되었습니다."),
    GET_ALL_POST_SUCCESS(HttpStatus.OK, "사용자가 작성한 전체 게시글 조회를 완료했습니다"),
    UPDATE_POST_SUCCESS(HttpStatus.OK, "게시글 수정이 완료되었습니다."),

    // 201 Created, Delete
    CREATE_POST_SUCCESS(HttpStatus.CREATED, "게시글 생성이 완료되었습니다."),
    DELETE_POST_SUCCESS(HttpStatus.NO_CONTENT, "게시글 삭제 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode(){
        return httpStatus.value();
    }
}

