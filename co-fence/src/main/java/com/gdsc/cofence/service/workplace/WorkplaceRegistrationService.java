package com.gdsc.cofence.service.workplace;

import com.gdsc.cofence.dto.AttendanceDto.AttendanceDto;
import com.gdsc.cofence.dto.userDto.AttendanceUserInfoDto;
import com.gdsc.cofence.dto.wokrplaceDto.WorkplaceIdDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.UserRegistrationResponseDto;
import com.gdsc.cofence.entity.attendence.Attendance;
import com.gdsc.cofence.entity.user.User;
import com.gdsc.cofence.entity.workplace.WorkPlace;
import com.gdsc.cofence.global.exception.ErrorCode;
import com.gdsc.cofence.global.exception.SuccessCode;
import com.gdsc.cofence.global.exception.model.CustomException;
import com.gdsc.cofence.repository.AttendanceRepository;
import com.gdsc.cofence.repository.UserRepository;
import com.gdsc.cofence.repository.WorkplaceRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class WorkplaceRegistrationService {

    private final WorkplaceRepository workplaceRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;

    // 해당 작업현장으로 출근하는 로직
    public UserRegistrationResponseDto checkInWorkplace(Long workplaceId, Principal principal) {

        if (principal == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_EXCEPTION, ErrorCode.UNAUTHORIZED_EXCEPTION.getMessage());
        }

        Long userId;
        try {
            userId = Long.parseLong(principal.getName());
        } catch (NumberFormatException e) {
            throw new CustomException(ErrorCode.INVALID_ID_EXCEPTION, ErrorCode.INVALID_ID_EXCEPTION.getMessage());
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION, "사용자: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        WorkPlace workPlace = workplaceRepository.findById(workplaceId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION, "작업 현장: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        AttendanceDto attendanceDto = AttendanceDto.builder()
                .userId(userId)
                .workplaceId(workplaceId)
                .checkInTime(LocalDateTime.now())
                .build();

        Attendance attendance = createAttendance(attendanceDto);
        attendanceRepository.save(attendance);

        AttendanceUserInfoDto userInfoDto = AttendanceUserInfoDto.builder()
                .userSeq(user.getUserSeq())
                .name(user.getUserName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .profileImageUrl(user.getProfileImageUrl())
                .nationality(user.getNationality())
                .roleType(user.getRoleType().toString())
                .checkInTime(attendance.getAttendTime())
                .build();

        WorkplaceIdDto workplaceIdDto = WorkplaceIdDto.builder()
                .workplaceId(workPlace.getWorkplaceId())
                .checkInTime(attendanceDto.getCheckInTime())
                .build();

        return UserRegistrationResponseDto.builder()
                .workplaceDto(workplaceIdDto)
                .attendanceUserInfoDto(userInfoDto)
                .build();
    }

    // 출근을 처리하는 로직
    public Attendance createAttendance(AttendanceDto attendanceDto) {
        User user = userRepository.findById(attendanceDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "사용자: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        WorkPlace workPlace = workplaceRepository.findById(attendanceDto.getWorkplaceId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "작업현장: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        return Attendance.builder()
                .user(user)
                .workPlace(workPlace)
                .attendTime(attendanceDto.getCheckInTime())
                .build();
    }

    // 해당 작업현장에 출근한 근로자들을 불러오는 로직
    public List<AttendanceUserInfoDto> getAttendeesInWorkplace(Long workplaceId, Principal principal) {

        List<Attendance> attendances = attendanceRepository.findByWorkPlace_WorkplaceId(workplaceId);

        List<AttendanceUserInfoDto> userInfoDtoList = new ArrayList<>();

        for (Attendance attendance : attendances) {
            Long userId = attendance.getUser().getUserSeq();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                            "사용자: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

            AttendanceUserInfoDto userInfoDto = AttendanceUserInfoDto.builder()
                    .userSeq(user.getUserSeq())
                    .name(user.getUserName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .profileImageUrl(user.getProfileImageUrl())
                    .nationality(user.getNationality())
                    .roleType(user.getRoleType().toString())
                    .checkInTime(attendance.getAttendTime())
                    .build();

            userInfoDtoList.add(userInfoDto);
        }
        return userInfoDtoList;
    }

    // 현장 탈퇴 로직
    public String checkOutWorkplace(Long workplaceId, Principal principal) {

        Long userId;
        try {
            userId = Long.parseLong(principal.getName());
        } catch (NumberFormatException e) {
            throw new CustomException(ErrorCode.INVALID_ID_EXCEPTION, "사용자: "
                    + ErrorCode.INVALID_ID_EXCEPTION.getMessage());
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "사용자: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        Attendance attendance = attendanceRepository.findByUserAndWorkPlace_WorkplaceId(user, workplaceId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "출근부: " + ErrorCode.NOT_FOUND_ID_EXCEPTION));

        attendanceRepository.delete(attendance);

        return SuccessCode.DELETE_ATTENDANCE_SUCCESS.getMessage();
    }
}
