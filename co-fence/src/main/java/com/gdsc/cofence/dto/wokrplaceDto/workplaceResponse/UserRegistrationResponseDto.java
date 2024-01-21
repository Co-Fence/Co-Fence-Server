package com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse;

import com.gdsc.cofence.dto.userDto.AttendanceUserInfoDto;
import com.gdsc.cofence.dto.userDto.UserInfoDto;
import com.gdsc.cofence.dto.wokrplaceDto.WorkplaceIdDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserRegistrationResponseDto {

    private WorkplaceIdDto workplaceId;
    private AttendanceUserInfoDto attendanceUserInfoDto;
}
