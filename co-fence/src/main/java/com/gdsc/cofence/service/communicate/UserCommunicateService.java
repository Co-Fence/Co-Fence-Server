package com.gdsc.cofence.service.communicate;

import com.gdsc.cofence.dto.userDto.UserInfoDto;
import com.gdsc.cofence.dto.userDto.userRequest.UserNameRequestDto;
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

    // 해당 작업현장에 근무하고있는 근로자, 관리자들을 리스트로 반환하는 로직
    public List<UserListResponseDto> getUserInfoList(Principal principal) {

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

    // 이름으로 동일한 작업현장에 있는 근로자, 관리자를 검색하는 로직
    public List<UserListResponseDto> searchUserByName(UserNameRequestDto userNameRequestDto, Principal principal) {
        String userName = userNameRequestDto.getUserName();

        Long userId = Long.parseLong(principal.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "사용자: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        Attendance latestAttendance = user.getAttendances().stream()
                .max(Comparator.comparing(Attendance::getAttendTime))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ATTENDANCE_EXCEPTION,
                        ErrorCode.NOT_FOUND_ATTENDANCE_EXCEPTION.getMessage()));

        WorkPlace workPlace = latestAttendance.getWorkPlace();

        List<User> users = workPlace.getAttendances().stream()
                .map(Attendance::getUser)
                .filter(user1 -> user1.getUserName().equals(userName))
                .collect(Collectors.toList());

        if (users.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION,
                    ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage());
        }

        return users.stream()
                .map(user1 -> UserListResponseDto.builder()
                        .userId(user1.getUserSeq())
                        .userName(user1.getUserName())
                        .profileImageUrl(user1.getProfileImageUrl())
                        .roleType(user1.getRoleType().toString())
                        .build())
                .collect(Collectors.toList());
    }


    // 사용자 상세 정보 조회하는 로직
    public UserInfoDto getUserDetailInfo(Long userId, Principal principal) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "사용자: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        return UserInfoDto.builder()
                .name(user.getUserName())
                .profileImageUrl(user.getProfileImageUrl())
                .roleType(user.getRoleType().toString())
                .nationality(user.getNationality())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
