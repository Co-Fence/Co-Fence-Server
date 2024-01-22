package com.gdsc.cofence.service.report;

import com.gdsc.cofence.dto.reportNotice.reportRequest.ReportRegistrationRequestDto;
import com.gdsc.cofence.dto.reportNotice.reportResponse.ReportRegistrationResponseDto;
import com.gdsc.cofence.entity.attendence.Attendance;
import com.gdsc.cofence.entity.report.ActionStatus;
import com.gdsc.cofence.entity.report.ReportManagement;
import com.gdsc.cofence.entity.report.ReportStatus;
import com.gdsc.cofence.entity.user.User;
import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import com.gdsc.cofence.repository.AttendanceRepository;
import com.gdsc.cofence.repository.ReportRepository;
import com.gdsc.cofence.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;


@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class ReportRegisterService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;

    // 신고 등록하는 메서드
    public ReportRegistrationResponseDto registerReport(ReportRegistrationRequestDto requestDto, Principal principal) {

        Long userId;
        try {
            userId = Long.parseLong(principal.getName());
        } catch (NumberFormatException e) {
            throw new CustomException(ErrorCode.INVALID_ID_EXCEPTION, ErrorCode.INVALID_ID_EXCEPTION.getMessage());
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "사용자:" + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        ReportStatus reportStatus = ReportStatus.fromDisplayName(requestDto.getReportStatus());
        String reportImageUrl = String.join(",", requestDto.getReportImageUrls());
        String reportSubject = reportStatus.getDisplayName();
        Long workplaceId = getLatestWorkplaceIdByUserSeq(user.getUserSeq());

        ReportManagement reportManagement = ReportManagement.builder()
                .reportSubject(reportSubject)
                .reportDetail(requestDto.getReportDetail())
                .reportImageUrl(reportImageUrl)
                .createdAt(requestDto.getCreatedAt())
                .actionStatus(ActionStatus.BEFORE_ACTION)
                .reportStatus(reportStatus)
                .user(user)
                // 해당 작업장에 이런 신고내용이 들어왔었음을 기록하는 용도의 Attribute
                // 신고한 사용자가 작업장을 바꿔도 이 작업장에서 이런 신고를 이 사용자가 했음의 데이터는 변화가 있어서는 안된다고 생각하기에 이렇게 했음
                .reportedWorkplaceId(workplaceId)
                .build();

        reportRepository.save(reportManagement);

        return new ReportRegistrationResponseDto(reportManagement);
    }

    // 해당 근로자의 출근시간을 이용해서 현재 사용자가 근무중인 작업현장의 Id를 반환하는 메서드
    private Long getLatestWorkplaceIdByUserSeq(Long userSeq) {
        Attendance latestAttendance = attendanceRepository.findFirstByUser_UserSeqOrderByAttendTimeDesc(userSeq);

        if (latestAttendance == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_WORK_RECORD_EXCEPTION,
                    ErrorCode.NOT_FOUND_WORK_RECORD_EXCEPTION.getMessage());
        }

        return latestAttendance.getWorkPlace().getWorkplaceId();
    }
}
