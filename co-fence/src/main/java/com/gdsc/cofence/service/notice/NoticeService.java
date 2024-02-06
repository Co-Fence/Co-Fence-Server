package com.gdsc.cofence.service.notice;

import com.gdsc.cofence.dto.noticeDto.NoticeMetaDto;
import com.gdsc.cofence.dto.noticeDto.noticeRequest.NoticeSearchRequestDto;
import com.gdsc.cofence.dto.noticeDto.noticeResponse.NoticeDetailResponseDto;
import com.gdsc.cofence.dto.noticeDto.noticeResponse.NoticeResponseWrapperDto;
import com.gdsc.cofence.dto.noticeDto.noticeResponse.NoticeSearchResponseDto;
import com.gdsc.cofence.entity.attendence.Attendance;
import com.gdsc.cofence.entity.notice.Notice;
import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import com.gdsc.cofence.repository.AttendanceRepository;
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
    private final AttendanceRepository attendanceRepository;

    // 공지사항을 검색하고 페이지네이션을 통해서 검색 결과를 반환하는 로직
    @Transactional(readOnly = true)
    public NoticeResponseWrapperDto searchNotices(NoticeSearchRequestDto requestDto, Principal principal,
                                                  int page, int size) {

        Long userId = Long.parseLong(principal.getName());
        Long workplaceId = getLatestWorkplaceIdByUserSeq(userId);

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<Notice> notices = noticeRepository
                .findByWorkPlace_WorkplaceIdAndNoticeSubjectContainingAndTargetRole
                        (workplaceId, requestDto.getNoticeSubject(), requestDto.getTargetRoletype(), pageable);

        if (notices == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_NOTICE_EXCEPTION,
                    ErrorCode.NOT_FOUND_NOTICE_EXCEPTION.getMessage());
        }

        List<NoticeSearchResponseDto> noticesDto = notices.stream()
                .map(notice -> NoticeSearchResponseDto.builder()
                        .noticeId(notice.getNoticeId())
                        .noticeSubject(notice.getNoticeSubject())
                        .targetRoleType(notice.getTargetRole())
                        .existImage(!notice.getNoticeImageUrl().isEmpty())
                        .build())
                .collect(Collectors.toList());

        NoticeMetaDto meta = NoticeMetaDto.builder()
                .count((int) notices.getTotalElements())
                .page(notices.getNumber() + 1)
                .hasMore(hasMore(page, size))
                .build();

        return NoticeResponseWrapperDto.builder()
                .meta(meta)
                .data(noticesDto)
                .build();
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
                .targetRoleType(notice.getTargetRole())
                .createdAt(notice.getCreatedAt())
                .noticeDetail(notice.getNoticeDetail())
                .noticeImage(notice.getNoticeImageUrl())
                .build();
    }

    // 더 표현할 데이터가 있는지에 대한 검증
    // true면 더 조회할 데이터가 있다는 것 / false면 더 조회할 데이터가 없다는것이다
    // findAll()을 사용안하는 방법으로 한 번 찾아봤음
    private Boolean hasMore(int page, int size) {
        long totalElements = noticeRepository.count();

        return totalElements > (long) page * size;
    }

    // 해당 근로자의 출근시간을 이용해서 현재 사용자가 근무중인 작업현장의 Id를 반환하는 메서드
    private Long getLatestWorkplaceIdByUserSeq(Long userSeq) {
        Attendance latestAttendance = attendanceRepository.findFirstByUser_UserSeqOrderByAttendTimeDesc(userSeq);

        if (latestAttendance == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_WORK_RECORD_EXCEPTION,
                    ErrorCode.NOT_FOUND_WORK_RECORD_EXCEPTION.getMessage());
        }

        return latestAttendance.getWorkPlace().getWorkplaceId();
    }
}
