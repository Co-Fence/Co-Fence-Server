package com.gdsc.cofence.dto.reportNotice.reportResponse;

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
}
