package com.gdsc.cofence.dto.reportNotice.reportResponse;

import com.gdsc.cofence.entity.report.ReportManagement;
import com.gdsc.cofence.entity.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportRegistrationResponseDto {

    private Long reportId;
    private String reportSubject;
    private String reportDetail;
    private List<String> reportImageUrls;
    private LocalDateTime createdAt;
    private String reportStatus;
    private String actionStatus;
    private Long userSeq;
    private Long reportedWorkplaceId;

    public ReportRegistrationResponseDto(ReportManagement reportManagement) {
        this.reportId = reportManagement.getReportId();
        this.reportSubject = reportManagement.getReportSubject();
        this.reportDetail = reportManagement.getReportDetail();
        this.reportImageUrls = Arrays.asList(reportManagement.getReportImageUrl().split(","));
        this.createdAt = reportManagement.getCreatedAt();
        this.reportStatus = reportManagement.getReportStatus().getDisplayName();
        this.actionStatus = reportManagement.getActionStatus().getDisplayName();
        this.userSeq = reportManagement.getUser().getUserSeq();
        this.reportedWorkplaceId = reportManagement.getReportedWorkplace().getWorkplaceId();
    }
}
