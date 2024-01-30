package com.gdsc.cofence.service.notice;

import com.gdsc.cofence.dto.noticeDto.noticeRequest.NoticeSearchRequestDto;
import com.gdsc.cofence.dto.noticeDto.noticeResponse.NoticeDetailResponseDto;
import com.gdsc.cofence.dto.noticeDto.noticeResponse.NoticeSearchResponseDto;
import com.gdsc.cofence.entity.notice.Notice;
import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import com.gdsc.cofence.repository.NoticeRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 공지사항을 검색하고 페이지네이션을 통해서 검색 결과를 반환하는 로직
    @Transactional(readOnly = true)
    public Page<NoticeSearchResponseDto> searchNotices(NoticeSearchRequestDto requestDto, Principal principal,
                                                       int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Notice> notices = noticeRepository
                .findByNoticeSubjectContainsAndTargetRole(requestDto.getNoticeSubject(), requestDto.getTargetRoletype(), pageable);

        if (notices == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_NOTICE_EXCEPTION,
                    ErrorCode.NOT_FOUND_NOTICE_EXCEPTION.getMessage());
        }

        return notices.map(notice -> NoticeSearchResponseDto.builder()
                .noticeId(notice.getNoticeId())
                .noticeSubject(notice.getNoticeSubject())
                .targetRoletype(notice.getTargetRole())
                .build());
    }

    // 공지사항 Id를 통해서 해당 공지사항의 세부사항을 조회하는 로직
    @Transactional(readOnly = true)
    public NoticeDetailResponseDto getNoticeDetail(Long noticeId, Principal principal) {

        Optional<Notice> noticeOptional = noticeRepository.findById(noticeId);

        if (!noticeOptional.isPresent()) {
            throw new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                    "공지사항: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage());
        }

        Notice notice = noticeOptional.get();

        return NoticeDetailResponseDto.builder()
                .noticeSubject(notice.getNoticeSubject())
                .userName(notice.getUser().getUserName())
                .targetRole(notice.getTargetRole())
                .createdAt(notice.getCreatedAt())
                .noticeDetail(notice.getNoticeDetail())
                .noticeImageUrl(notice.getNoticeImageUrl())
                .build();
    }
}
