package com.gdsc.cofence.controller.report;

import com.gdsc.cofence.dto.reportNotice.reportRequest.ReportRegistrationRequestDto;
import com.gdsc.cofence.dto.reportNotice.reportRequest.ReportSearchRequestDto;
import com.gdsc.cofence.dto.reportNotice.reportResponse.ReportRegistrationResponseDto;
import com.gdsc.cofence.dto.reportNotice.reportResponse.ReportSearchDetailResponseDto;
import com.gdsc.cofence.dto.reportNotice.reportResponse.ReportSearchResponseDto;
import com.gdsc.cofence.service.report.ReportRegisterService;
import com.gdsc.cofence.service.report.ReportSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "신고 관리")
@RequestMapping("/api/v22/report")
public class ReportController {

    private final ReportRegisterService reportRegisterService;
    private final ReportSearchService reportSearchService;

    @PostMapping("/register")
    @Operation(summary = "신고등록하는 api", description = "requestBody를 통해서 신고관련 정보를 받아서 해당 신고에 대한 정보를 반환합니다. ")
    public ResponseEntity<ReportRegistrationResponseDto> registerReport(@RequestBody ReportRegistrationRequestDto requestDto, Principal principal) {
        ReportRegistrationResponseDto responseDto = reportRegisterService.registerReport(requestDto, principal);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/search")
    @Operation(summary = "신고 목록들을 조회하는 api", description = "신고를 검색하는데 필요한 정보들과 page, size를 파라미터 값으로 받아서 해당하는 검색 결과를 반환합니다.")
    public ResponseEntity<Page<ReportSearchResponseDto>> searchReports(@RequestBody ReportSearchRequestDto requestDto,
                                                                       @RequestParam int page, @RequestParam int size,
                                                                       Principal principal) {
        Page<ReportSearchResponseDto> result = reportSearchService.searchReports(requestDto, page, size, principal);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/detail/{reportId}")
    @Operation(summary = "신고Id를 통해서 신고 상세내역을 조회", description = "reportId를 받아서 해당 신고 내역에 대한 상세 정보를 조회합니다. ")
    public ResponseEntity<ReportSearchDetailResponseDto> getDetailReport(@PathVariable Long reportId, Principal principal) {

        ReportSearchDetailResponseDto result = reportSearchService.getReportDetail(reportId, principal);

        return ResponseEntity.ok(result);
    }
}
