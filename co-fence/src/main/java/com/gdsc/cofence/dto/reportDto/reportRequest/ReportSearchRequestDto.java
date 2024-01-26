package com.gdsc.cofence.dto.reportDto.reportRequest;

import com.gdsc.cofence.entity.report.ActionStatus;
import com.gdsc.cofence.entity.report.ReportStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ReportSearchRequestDto {

    private String actionStatus;
    private String reportStatus;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public ActionStatus getActionStatusEnum() {
        return ActionStatus.fromDisplayName(this.actionStatus);
    }

    public ReportStatus getReportStatusEnum() {
        return ReportStatus.fromDisplayName(this.reportStatus);
    }
}
