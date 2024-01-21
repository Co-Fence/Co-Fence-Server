package com.gdsc.cofence.controller.report;

import com.gdsc.cofence.dto.reportNotice.reportRequest.ReportRegistrationRequestDto;
import com.gdsc.cofence.dto.reportNotice.reportResponse.ReportRegistrationResponseDto;
import com.gdsc.cofence.service.report.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
@Tag(name = "신고 관리")
@RequestMapping("/api/v22/report")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/register")
    @Operation(summary = "신고등록하는 api", description = "requestBody를 통해서 신고관련 정보를 받아서 해당 신고에 대한 정보를 반환합니다. ")
    public ResponseEntity<ReportRegistrationResponseDto> registerReport(@RequestBody ReportRegistrationRequestDto requestDto, Principal principal) {
        ReportRegistrationResponseDto responseDto = reportService.registerReport(requestDto, principal);

        return ResponseEntity.ok(responseDto);
    }
}
