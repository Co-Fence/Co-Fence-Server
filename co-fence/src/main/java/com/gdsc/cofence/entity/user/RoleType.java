package com.gdsc.cofence.entity.user;

import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    ROLE_USER("ROLE_USER", "일반 사용자"),
    ROLE_ADMIN("ROLE_ADMIN", "관리자"),
    ROLE_ALL("ALL", "전체 사용자");

    private final String code;
    private final String displayName;

    public static RoleType fromString(String role) {
        for (RoleType type : RoleType.values()) {
            if (type.code.equalsIgnoreCase(role)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No RoleType with text" + role + "found");
    }

    public static RoleType getRoleTypeOfString(String roleType) {
        for (RoleType type : RoleType.values()) {
            if (type.code.substring(5).equals(roleType)) {
                return type;
            }
        }

        throw new CustomException(ErrorCode.INVALID_ROLE_TYPE_EXCEPTION, ErrorCode.INVALID_TOKEN_EXCEPTION.getMessage());
    }
}
