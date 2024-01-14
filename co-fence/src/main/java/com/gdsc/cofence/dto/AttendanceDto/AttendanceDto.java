package com.gdsc.cofence.dto.AttendanceDto;

import com.gdsc.cofence.entity.attendence.Attendance;
import com.gdsc.cofence.entity.user.User;
import com.gdsc.cofence.entity.workplace.WorkPlace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {

    private Long userId;
    private Long workplaceId;
    private LocalDateTime checkInTime;
}
