package com.gdsc.cofence.service.notice;

import com.gdsc.cofence.dto.noticeDto.noticeRequest.NoticeRegisterRequestDto;
import com.gdsc.cofence.dto.noticeDto.noticeResponse.NoticeRegisterResponseDto;
import com.gdsc.cofence.entity.notice.Notice;
import com.gdsc.cofence.entity.user.User;
import com.gdsc.cofence.entity.workplace.WorkPlace;
import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import com.gdsc.cofence.repository.NoticeRepository;
import com.gdsc.cofence.repository.UserRepository;
import com.gdsc.cofence.repository.WorkplaceRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
public class NoticeAdminService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;
    private final WorkplaceRepository workplaceRepository;

    // 공지사항을 등록하는 로직
    // 작업현장Id에 매칭해서 해야하는 수행과제가 있음
    public NoticeRegisterResponseDto registerNoticeOnlyAdmin(NoticeRegisterRequestDto requestDto, Principal principal) {
        Long userId = Long.parseLong(principal.getName());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "사용지: " + ErrorCode.NOT_FOUND_ID_EXCEPTION));

        WorkPlace workPlace = workplaceRepository.findById(requestDto.getWorkplaceId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        "작업장: " + ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage()));

        Notice notice = Notice.builder()
                .noticeSubject(requestDto.getNoticeSubject())
                .targetRole(requestDto.getTargetRoleType())
                .noticeDetail(requestDto.getNoticeDetail())
                .noticeImageUrl(requestDto.getNoticeImageUrls())
                .user(user)
                .workPlace(workPlace)
                .build();

        noticeRepository.save(notice);

        return NoticeRegisterResponseDto.builder()
                .noticeId(notice.getNoticeId())
                .noticeSubject(notice.getNoticeSubject())
                .userName(user.getUserName())
                .targetRoleType(notice.getTargetRole())
                .createdAt(notice.getCreatedAt())
                .noticeDetail(notice.getNoticeDetail())
                .noticeImage(notice.getNoticeImageUrl())
                .build();
    }
}
