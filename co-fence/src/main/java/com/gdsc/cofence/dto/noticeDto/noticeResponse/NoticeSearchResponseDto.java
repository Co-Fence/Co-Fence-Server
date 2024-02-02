package com.gdsc.cofence.dto.noticeDto.noticeResponse;

import com.gdsc.cofence.entity.user.RoleType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class NoticeSearchResponseDto {

    private Long noticeId;
    private String noticeSubject;
    private RoleType targetRoletype;
    private Boolean existImage;
}
