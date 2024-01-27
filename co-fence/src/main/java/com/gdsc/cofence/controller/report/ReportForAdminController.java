package com.gdsc.cofence.controller.report;

import com.gdsc.cofence.dto.reportDto.reportRequest.ReportUpdateRequestDto;
import com.gdsc.cofence.dto.reportDto.reportResponse.ReportUpdateResponseDto;
import com.gdsc.cofence.service.report.ReportAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "관리자의 신고 관리")
@RequestMapping("/api/v22/reportForAdmin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ReportForAdminController {


    private final ReportAdminService reportAdminService;


    @PutMapping("/update/{reportId}")
    @Operation(summary = "신고내용을 관리자가 수정", description = "reportId와 수정할 내용을 requestBody로 받아서 관리자만이 해당 신고내용을 수정하고 그 결과를 반환합니다. ")
    public ResponseEntity<ReportUpdateResponseDto> updateReportOnlyAdMin(@PathVariable Long reportId,
                                                                         @RequestBody ReportUpdateRequestDto requestDto,
                                                                         Principal principal) {
        ReportUpdateResponseDto updateResult = reportAdminService.updateReport(reportId, requestDto, principal);
        return ResponseEntity.ok(updateResult);
    }

    @DeleteMapping("/delete/{reportId}")
    @Operation(summary = "신고 삭제", description = "reportId를 받아서 해당 신고를 삭제합니다")
    public ResponseEntity<String> deleteReportByAdmin(@PathVariable Long reportId, Principal principal) {

        String deleteSuccess = reportAdminService.deleteReport(reportId, principal);

        return ResponseEntity.ok(deleteSuccess);
    }
}
