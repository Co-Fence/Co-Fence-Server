package com.gdsc.cofence.controller.notice;


import com.gdsc.cofence.dto.noticeDto.noticeRequest.NoticeSearchRequestDto;
import com.gdsc.cofence.dto.noticeDto.noticeResponse.NoticeDetailResponseDto;
import com.gdsc.cofence.dto.noticeDto.noticeResponse.NoticeSearchResponseDto;
import com.gdsc.cofence.service.notice.NoticeService;
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
import java.util.List;

@RestController
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "공지사항")
@RequestMapping("/api/v22/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/search")
    @Operation(summary = "공지사항 검색", description = "공지사항을 requestBody를 통해서 json으로 받고 파라미터값으로 page, size값을 받습니다. 페이지네이션을 통해서 검색 결과를 반환합니다.")
    public ResponseEntity<Page<NoticeSearchResponseDto>> searchNotices(@RequestBody NoticeSearchRequestDto requestDto, Principal principal,
                                                                       @RequestParam int page, @RequestParam int size) {
        Page<NoticeSearchResponseDto> results = noticeService.searchNotices(requestDto, principal, page, size);

        return ResponseEntity.ok(results);
    }

    @GetMapping("/detail/{noticeId}")
    @Operation(summary = "공자사항 세부내용 조회", description = "noticeId를 받아서 해당 공지사항의 세부사항을 조회합니다. ")
    public ResponseEntity<NoticeDetailResponseDto> getNoticeDetail(@PathVariable Long noticeId, Principal principal) {

        NoticeDetailResponseDto result = noticeService.getNoticeDetail(noticeId, principal);

        return ResponseEntity.ok(result);
    }
}
