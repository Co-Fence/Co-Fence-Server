package com.gdsc.cofence.dto.userDto.userResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserListResponseDto {

    private Long userId;
    private String userName;
    private String profileImageUrl;
    private String roleType;
}
