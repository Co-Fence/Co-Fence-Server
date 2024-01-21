package com.gdsc.cofence.dto.userDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDto { // 받아오는 정보대로 수정완료
    private String name;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;
    private String nationality;
    private String roleType;
}
