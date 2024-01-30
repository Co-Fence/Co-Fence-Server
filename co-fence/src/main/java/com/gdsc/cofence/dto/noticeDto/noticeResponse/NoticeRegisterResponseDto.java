package com.gdsc.cofence.dto.noticeDto.noticeResponse;

import com.gdsc.cofence.entity.user.RoleType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeRegisterResponseDto {

    private Long noticeId;
    private String noticeSubject;
    private String userName;
    private RoleType targetRoleType;
    private LocalDateTime createdAt;
    private String noticeDetail;
    private List<String> noticeImage;
}
