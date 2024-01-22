package com.gdsc.cofence.service.report;

import com.gdsc.cofence.dto.reportNotice.reportRequest.ReportSearchRequestDto;
import com.gdsc.cofence.dto.reportNotice.reportResponse.ReportSearchResponseDto;
import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import com.gdsc.cofence.repository.ReportRepository;
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
@Transactional
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportSearchService {

    private final ReportRepository reportRepository;

    public Page<ReportSearchResponseDto> searchReports(ReportSearchRequestDto requestDto, int page, int size, Principal principal) {

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
                .and(ReportSpecification.createdBetween(requestDto.getStartDateTime(), requestDto.getEndDateTime()));

        Pageable pageable = PageRequest.of(page, size);

        Page<ReportManagement> reports = reportRepository.findAll(spec, pageable);

        // 해당 검색결과에 대한 신고 내역이 없는 경우의 예외처리
        if (reports.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_REPORTS_EXCEPTION,
                    ErrorCode.NOT_FOUND_REPORTS_EXCEPTION.getMessage());
        }

        return reports.map(reportManagement -> new ReportSearchResponseDto(
                reportManagement.getReportId(),
                reportManagement.getReportStatus().name(),
                reportManagement.getReportSubject(),
                reportManagement.getCreatedAt()
        ));
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
    }
}
