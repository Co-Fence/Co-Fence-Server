package com.gdsc.cofence.dto.reportDto.reportResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportUpdateResponseDto {

    private Long reportId;
    private String reportSubject;
    private String actionStatus;
    private String reportStatus;
    private LocalDateTime modifiedAt;
}
