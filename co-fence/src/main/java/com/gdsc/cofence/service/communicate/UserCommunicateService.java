package com.gdsc.cofence.service.communicate;

import com.gdsc.cofence.dto.userDto.UserInfoDto;
import com.gdsc.cofence.dto.userDto.userResponse.UserListResponseDto;
import com.gdsc.cofence.entity.attendence.Attendance;
import com.gdsc.cofence.entity.user.User;
import com.gdsc.cofence.entity.workplace.WorkPlace;
import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import com.gdsc.cofence.repository.AttendanceRepository;
import com.gdsc.cofence.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
public class UserCommunicateService {

    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;

    public List<UserListResponseDto> getWorkplaceUserInfoList(Principal principal) {

        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "사용자: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        Attendance latestAttendance = user.getAttendances().stream()
                .max(Comparator.comparing(Attendance::getAttendTime))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ATTENDANCE_EXCEPTION,
                        ErrorCode.NOT_FOUND_ATTENDANCE_EXCEPTION.getMessage()));

        WorkPlace workPlace = latestAttendance.getWorkPlace();

        return workPlace.getAttendances().stream()
                .map(Attendance::getUser)
                .map(User::toDto)
                .collect(Collectors.toList());
    }

    public UserInfoDto getUserDetailInfo(Long userId, Principal principal) {

        Long adminUserId = Long.parseLong(principal.getName());
        Long adminWorkplaceId = getLatestWorkplaceIdByUserSeq(adminUserId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "사용자: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        Long userWorkplaceId = getLatestWorkplaceIdByUserSeq(userId);

        if (adminWorkplaceId.equals(userWorkplaceId)) {
            throw new CustomException(ErrorCode.ONLY_OWN_WORKPLACE_USER_INQUIRY_EXCEPTION,
                    ErrorCode.ONLY_OWN_WORKPLACE_USER_INQUIRY_EXCEPTION.getMessage());
        }

        return UserInfoDto.builder()
                .name(user.getUserName())
                .nationality(user.getNationality())
                .roleType(user.getRoleType().toString())
                .profileImageUrl(user.getProfileImageUrl())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    private Long getLatestWorkplaceIdByUserSeq(Long userSeq) {
        Attendance latestAttendance = attendanceRepository.findFirstByUser_UserSeqOrderByAttendTimeDesc(userSeq);

        if (latestAttendance == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_WORK_RECORD_EXCEPTION,
                    ErrorCode.NOT_FOUND_WORK_RECORD_EXCEPTION.getMessage());
        }

        return latestAttendance.getWorkPlace().getWorkplaceId();
    }
}
