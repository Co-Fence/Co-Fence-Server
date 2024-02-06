package com.gdsc.cofence.dto.noticeDto.noticeResponse;

import com.gdsc.cofence.entity.user.RoleType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class NoticeDetailResponseDto {

    private String noticeSubject;
    private String userName;
    private RoleType targetRoleType;
    private LocalDateTime createdAt;
    private String noticeDetail;
    private List<String> noticeImage;
}
