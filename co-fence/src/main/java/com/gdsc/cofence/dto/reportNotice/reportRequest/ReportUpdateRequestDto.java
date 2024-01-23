package com.gdsc.cofence.dto.reportNotice.reportRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportUpdateRequestDto {

    private String reportSubject;
    private String actionStatus;
    private String reportStatus;
}
