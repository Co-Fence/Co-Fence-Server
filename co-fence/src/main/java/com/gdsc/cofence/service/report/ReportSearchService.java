package com.gdsc.cofence.service.report;

import com.gdsc.cofence.dto.reportDto.reportRequest.ReportSearchRequestDto;
import com.gdsc.cofence.dto.reportDto.reportResponse.ReportSearchDetailResponseDto;
import com.gdsc.cofence.dto.reportDto.reportResponse.ReportSearchResponseDto;
import com.gdsc.cofence.entity.attendence.Attendance;
import com.gdsc.cofence.entity.user.User;
import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import com.gdsc.cofence.repository.AttendanceRepository;
import com.gdsc.cofence.repository.ReportRepository;
import com.gdsc.cofence.repository.UserRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gdsc.cofence.entity.report.ActionStatus;
import com.gdsc.cofence.entity.report.ReportManagement;
import com.gdsc.cofence.entity.report.ReportStatus;
import org.springframework.data.jpa.domain.Specification;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportSearchService {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final AttendanceRepository attendanceRepository;

    // 신고 목록을 검색하는 로직
    public Page<ReportSearchResponseDto> searchReports(ReportSearchRequestDto requestDto, int page, int size, Principal principal) {

        // 잘못된 인증처리에 해당하는 예외처리
        if (principal == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_EXCEPTION,
                    ErrorCode.UNAUTHORIZED_EXCEPTION.getMessage());
        }

        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "사용자: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        Long reportingWorkplaceId = getLatestWorkplaceIdByUserSeq(user.getUserSeq());

        // 잘못된 page, size의 번호 처리
        if (page < 0 || size < 0) {
            throw new CustomException(ErrorCode.NUMBER_LESS_THAN_ZERO_EXCEPTION,
                    ErrorCode.NUMBER_LESS_THAN_ZERO_EXCEPTION.getMessage());
        }

        // startDateTime이 endDateTime보다 늦는경우의 예외처리
        if (requestDto.getStartDateTime().isAfter(requestDto.getStartDateTime())) {
            throw new CustomException(ErrorCode.AFTER_END_DATE_EXCEPTION,
                    ErrorCode.AFTER_END_DATE_EXCEPTION.getMessage());
        }

        Specification<ReportManagement> spec = Specification
                .where(ReportSpecification.hasActionStatus(requestDto.getActionStatusEnum()))
                .and(ReportSpecification.hasReportStatus(requestDto.getReportStatusEnum()))
                .and(ReportSpecification.createdBetween(requestDto.getStartDateTime(), requestDto.getEndDateTime()))
                .and(ReportSpecification.hasWorkplaceId(reportingWorkplaceId));

        Pageable pageable = PageRequest.of(page, size);

        Page<ReportManagement> reports = reportRepository.findAll(spec, pageable);

        // 해당 검색결과에 대한 신고 내역이 없는 경우의 예외처리
        if (reports.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_REPORTS_EXCEPTION,
                    ErrorCode.NOT_FOUND_REPORTS_EXCEPTION.getMessage());
        }

        return reports.map(reportManagement -> new ReportSearchResponseDto(reportManagement));
    }

    private class ReportSpecification {

        public static Specification<ReportManagement> hasActionStatus(ActionStatus actionStatus) {
            return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("actionStatus"), actionStatus));
        }

        public static Specification<ReportManagement> hasReportStatus(ReportStatus reportStatus) {
            return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("reportStatus"), reportStatus));
        }

        public static Specification<ReportManagement> createdBetween(LocalDateTime start, LocalDateTime end) {
            return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("createdAt"), start, end));
        }

        public static Specification<ReportManagement> hasWorkplaceId(Long workplaceId) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("reportedWorkplace").get("workplaceId"), workplaceId);
        }
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

    // reportId로 해당 신고의 상세내역을 조회하는 기능
    public ReportSearchDetailResponseDto getReportDetail(Long reportId, Principal principal) {
        ReportManagement reportManagement = reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "신고: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        return ReportSearchDetailResponseDto.builder()
                .reportSubject(reportManagement.getReportSubject())
                .actionStatus(reportManagement.getActionStatus().getDisplayName())
                .reportStatus(reportManagement.getReportStatus().getDisplayName())
                .reportImageUrl(reportManagement.getReportImageUrl())
                .reportDetail(reportManagement.getReportDetail())
                .userName(reportManagement.getUser().getUserName())
                .createAt(reportManagement.getCreatedAt())
                .build();
    }
}
