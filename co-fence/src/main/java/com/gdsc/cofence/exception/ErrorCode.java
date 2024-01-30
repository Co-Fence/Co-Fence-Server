package com.gdsc.cofence.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 400 Bad Request
    VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    VALIDATION_REQUEST_MISSING_EXCEPTION(HttpStatus.BAD_REQUEST, "필수적인 요청 값이 입력되지 않았습니다."),
    VALIDATION_REQUEST_HEADER_MISSING_EXCEPTION(HttpStatus.BAD_REQUEST, "요청 헤더값이 입력되지 않았습니다."),
    VALIDATION_REQUEST_PARAMETER_MISSING_EXCEPTION(HttpStatus.BAD_REQUEST, "요청 파라미터값이 입력되지 않았습니다."),
    REQUEST_METHOD_VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "요청 메소드가 잘못됐습니다."),
    VALIDATION_REQUEST_FAIL_USERINFO_EXCEPTION(HttpStatus.BAD_REQUEST,"사용자 정보를 받아오는데 실패했습니다."),
    VALIDATION_JSON_SYNTAX_FAIL(HttpStatus.BAD_REQUEST, "JSON 파싱 오류 발생"),
    INVALID_ROLE_TYPE_EXCEPTION(HttpStatus.BAD_REQUEST, "올바르지 않은 권한 요청입니다."),
    INVALID_ID_EXCEPTION(HttpStatus.BAD_REQUEST, "사용자 ID가 유효하지 않습니다. "),
    INVALID_SIGNATURE_EXCEPTION(HttpStatus.BAD_REQUEST, "JWT 토큰의 서명이 올바르지 않습니다."),
    INVALID_DISPLAY_NAME_EXCEPTION(HttpStatus.BAD_REQUEST, "유효하지 않은 displayName입니다. "),
    NUMBER_LESS_THAN_ZERO_EXCEPTION(HttpStatus.BAD_REQUEST, "페이지의 크기 번호나 페이지의 사이즈는 0 미만일 수 없습니다. "),
    AFTER_END_DATE_EXCEPTION(HttpStatus.BAD_REQUEST, "검색필터의 start date가 end date보다 늦을수 없습니다. "),

    // 401 Unauthorized
    UNAUTHORIZED_EMAIL_EXCEPTION(HttpStatus.UNAUTHORIZED, "이메일 인증이 되지 않은 유저"),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자거나 인증과정에 오류가 있습니다. "),
    INVALID_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    ONLY_OWN_WORKPLACE_REPORT_MODIFY_EXCEPTION(HttpStatus.UNAUTHORIZED, "관리자는 자신의 작업현장에서 발생한 신고만 수정할 수 있습니다."),
    ONLY_OWN_WORKPLACE_USER_INQUIRY_EXCEPTION(HttpStatus.UNAUTHORIZED, "관리자는 자신의 작업현장의 근로자만 조회할 수 있습니다. "),

    // 403 Forbidden
    FORBIDDEN_AUTH_EXCEPTION(HttpStatus.FORBIDDEN, "권한 정보가 없는 토큰입니다."),
    EXPIRED_TOKEN_EXCEPTION(HttpStatus.FORBIDDEN, "토큰이 만료되었습니다."),
    ACCESS_DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "접근 권한이 없습니다. "),
    AUTHENTICATION_FAILED_EXCEPTION(HttpStatus.FORBIDDEN, "인증에 실패했습니다. "),

    // 404 NOT FOUND
    NOT_FOUND_ID_EXCEPTION(HttpStatus.NOT_FOUND, "찾을 수 없는 ID"),
    NOT_FOUND_NOTICE_EXCEPTION(HttpStatus.NOT_FOUND, "해당 검색결과에 부합하는 공지사항이 없습니다. "),
    NOT_FOUND_EMAIL_EXCEPTION(HttpStatus.NOT_FOUND, "존재하지 않는 이메일"),
    NOT_FOUND_WORK_RECORD_EXCEPTION(HttpStatus.NOT_FOUND, "해당 사용자의 근무 기록이 존재하지 않습니다."),
    NOT_FOUND_REPORTS_EXCEPTION(HttpStatus.NOT_FOUND, "해당 검색 필터에 맞는 신고 내역들이 존재하지 않습니다"),
    NOT_FOUND_ATTENDANCE_EXCEPTION(HttpStatus.NOT_FOUND, "해당 사용자에 대한 출근 기록이 없습니다. "),
    NOT_FOUND_USER_EXCEPTION(HttpStatus.NOT_FOUND, "해당 검색결과에 해당하는 사용자를 찾을 수 없습니다."),

    // 409 Conflict
    ALREADY_EXIST_EMAIL_EXCEPTION(HttpStatus.CONFLICT, "이미 회원가입이 완료된 사용자입니다."),
    ALREADY_EXIST_SUBJECT_EXCEPTION(HttpStatus.CONFLICT, "이미 존재하는 제목입니다."),

    // 500 Internal Server Exception
    INTERNAL_SERVER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생했습니다."),
    TOKEN_CREATION_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "토큰을 생성하는 과정에서 알 수 없는 오류가 발생했습니다."),

    // 503 Service Unavailable
    FAILED_GET_TOKEN_EXCEPTION(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "구글 엑세스 토큰을 가져오는데 실패했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode(){
        return httpStatus.value();
    }
}

