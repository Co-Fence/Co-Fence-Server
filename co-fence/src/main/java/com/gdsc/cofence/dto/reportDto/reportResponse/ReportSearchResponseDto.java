package com.gdsc.cofence.dto.reportDto.reportResponse;

import com.gdsc.cofence.entity.report.ReportManagement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ReportSearchResponseDto {

    private Long reportId;
    private String reportStatus;
    private String reportSubject;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long reportedWorkplaceId;

    public ReportSearchResponseDto(ReportManagement reportManagement) {
        this.reportId = reportManagement.getReportId();
        this.reportSubject = reportManagement.getReportSubject();
        this.createdAt = reportManagement.getCreatedAt();
        this.modifiedAt = reportManagement.getModifiedAt();
        this.reportStatus = reportManagement.getReportStatus().getDisplayName();
        if (reportManagement.getReportedWorkplace() != null) {
            this.reportedWorkplaceId = reportManagement.getReportedWorkplace().getWorkplaceId();
        } else {
            this.reportedWorkplaceId = null;
        }
    }
}
