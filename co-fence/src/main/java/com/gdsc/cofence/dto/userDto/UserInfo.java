package com.gdsc.cofence.dto.userDto;

import com.gdsc.cofence.entity.user.RoleType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UserInfo { // 받아오는 정보대로 수정완료
    private String name;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;
    private String nationality;
    private String roleType;
}
