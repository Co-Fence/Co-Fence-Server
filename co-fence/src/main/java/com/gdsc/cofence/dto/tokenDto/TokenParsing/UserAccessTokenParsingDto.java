package com.gdsc.cofence.dto.tokenDto.TokenParsing;

import com.gdsc.cofence.entity.user.RoleType;
import com.gdsc.cofence.entity.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAccessTokenParsingDto {

    private Long userSeq;
    private RoleType roleType;
    private String email;

    @Builder
    public static UserAccessTokenParsingDto from(User user) {
        return UserAccessTokenParsingDto.builder()
                .userSeq(user.getUserSeq())
                .roleType(user.getRoleType())
                .email(user.getEmail())
                .build();
    }
}
