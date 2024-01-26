package com.gdsc.cofence.dto.reportDto.reportResponse;

import com.gdsc.cofence.entity.report.ActionStatus;
import com.gdsc.cofence.entity.report.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ReportSearchDetailResponseDto {

    private String reportSubject;
    private String actionStatus;
    private String reportStatus;
    private String reportImageUrl;
    private String reportDetail;
    private String userName;
    private LocalDateTime createAt;
    public ActionStatus getActionStatusEnum() {
        return ActionStatus.fromDisplayName(this.actionStatus);
    }

    public ReportStatus getReportStatusEnum() {
        return ReportStatus.fromDisplayName(this.reportStatus);
    }
}
