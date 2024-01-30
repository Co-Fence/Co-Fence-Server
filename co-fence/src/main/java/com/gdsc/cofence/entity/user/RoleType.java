package com.gdsc.cofence.entity.user;

import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    ROLE_USER("USER", "근로자"),
    ROLE_ADMIN("ADMIN", "관리자"),
    ROLE_ALL("ALL", "공통");

    private final String code;
    private final String displayName;

    public static RoleType getRoleTypeOfString(String roleType) {
        for (RoleType type : RoleType.values()) {
            if (type.code.equals(roleType)) {
                return type;
            }
        }

        throw new CustomException(ErrorCode.INVALID_ROLE_TYPE_EXCEPTION, ErrorCode.INVALID_TOKEN_EXCEPTION.getMessage());
    }
}
