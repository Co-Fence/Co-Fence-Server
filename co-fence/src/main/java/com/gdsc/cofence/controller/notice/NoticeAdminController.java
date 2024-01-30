package com.gdsc.cofence.controller.notice;

import com.gdsc.cofence.dto.noticeDto.noticeRequest.NoticeRegisterRequestDto;
import com.gdsc.cofence.dto.noticeDto.noticeResponse.NoticeRegisterResponseDto;
import com.gdsc.cofence.service.notice.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "공지사항")
@RequestMapping("/api/v22/noticeForAdmin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class NoticeAdminController {

    private final NoticeService noticeService;

    @PostMapping("/register")
    @Operation(summary = "공지사항 등록", description = "공지사항을 등록합니다.")
    public ResponseEntity<NoticeRegisterResponseDto> noticeRegister(@RequestBody NoticeRegisterRequestDto requestDto, Principal principal) {
        NoticeRegisterResponseDto result = noticeService.registerNoticeOnlyAdmin(requestDto, principal);

        return ResponseEntity.ok(result);
    }
}
