package com.gdsc.cofence.service.report;

import com.gdsc.cofence.dto.reportDto.reportRequest.ReportUpdateRequestDto;
import com.gdsc.cofence.dto.reportDto.reportResponse.ReportUpdateResponseDto;
import com.gdsc.cofence.entity.attendence.Attendance;
import com.gdsc.cofence.entity.report.ActionStatus;
import com.gdsc.cofence.entity.report.ReportManagement;
import com.gdsc.cofence.entity.report.ReportStatus;
import com.gdsc.cofence.entity.user.User;
import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.SuccessCode;
import com.gdsc.cofence.exception.model.CustomException;
import com.gdsc.cofence.repository.AttendanceRepository;
import com.gdsc.cofence.repository.ReportRepository;
import com.gdsc.cofence.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class ReportAdminService { // 관리자만이 실행 할 수 있는 로직들

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;

    // 신고를 수정하는 로직
    @Transactional
    public ReportUpdateResponseDto updateReport(Long reportId, ReportUpdateRequestDto requestDto, Principal principal) {

        User user = getUserByPrincipal(principal);

        Long WorkplaceId = getLatestWorkplaceIdByUserSeq(user.getUserSeq());

        ReportManagement reportManagement = reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "신고: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        if (!reportManagement.getReportedWorkplace().getWorkplaceId().equals(WorkplaceId)) {
            throw new CustomException(ErrorCode.ONLY_OWN_WORKPLACE_REPORT_MODIFY_EXCEPTION,
                    ErrorCode.ONLY_OWN_WORKPLACE_REPORT_MODIFY_EXCEPTION.getMessage());
        }

        reportManagement.updateReportOnlyAdmin(
                requestDto.getReportSubject(),
                ActionStatus.fromDisplayName(requestDto.getActionStatus()),
                ReportStatus.fromDisplayName(requestDto.getReportStatus()),
                LocalDateTime.now()
        );

        reportRepository.save(reportManagement);

        return ReportUpdateResponseDto.builder()
                .reportId(reportManagement.getReportId())
                .reportSubject(reportManagement.getReportSubject())
                .actionStatus(reportManagement.getActionStatus().getDisplayName())
                .reportStatus(reportManagement.getReportStatus().getDisplayName())
                .modifiedAt(reportManagement.getModifiedAt())
                .build();
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

    // 신고를 삭제하는 로직
    @Transactional
    public String deleteReport(Long reportId, Principal principal) {

        User user = getUserByPrincipal(principal);

        Long WorkplaceId = getLatestWorkplaceIdByUserSeq(user.getUserSeq());

        ReportManagement reportManagement = reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "신고: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        if (!reportManagement.getReportedWorkplace().getWorkplaceId().equals(WorkplaceId)) {
            throw new CustomException(ErrorCode.ONLY_OWN_WORKPLACE_REPORT_MODIFY_EXCEPTION,
                    ErrorCode.ONLY_OWN_WORKPLACE_REPORT_MODIFY_EXCEPTION.getMessage());
        }

        reportRepository.delete(reportManagement);

        return SuccessCode.DELETE_REPORT_SUCCESS.getMessage();
    }

    private Long findUserIdByPrincipal(String principalName) {
        return Long.parseLong(principalName);
    }

    private User getUserByPrincipal(Principal principal) {
        Long userId = findUserIdByPrincipal(principal.getName());

        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "사용자: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));
    }
}
