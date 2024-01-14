package com.gdsc.cofence.dto.userDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AttendanceUserInfoDto {
    private Long userSeq;
    private String name;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;
    private String nationality;
    private String roleType;
    private LocalDateTime checkInTime;
}
