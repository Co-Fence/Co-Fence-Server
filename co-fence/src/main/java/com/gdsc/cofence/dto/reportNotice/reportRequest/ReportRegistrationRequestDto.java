package com.gdsc.cofence.dto.reportNotice.reportRequest;

import com.gdsc.cofence.entity.report.ReportStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportRegistrationRequestDto {

    private Long workplaceId;
    private String reportSubject;
    private String reportDetail;
    private List<String> reportImageUrls;
    private LocalDateTime createdAt;
    private String reportStatus;

    public ReportStatus getReportStatusEnum() {
        return ReportStatus.valueOf(this.reportStatus);
    }
}
