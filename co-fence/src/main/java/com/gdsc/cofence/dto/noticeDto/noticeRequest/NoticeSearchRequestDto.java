package com.gdsc.cofence.dto.noticeDto.noticeRequest;

import com.gdsc.cofence.entity.user.RoleType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeSearchRequestDto {

    private String noticeSubject;
    private RoleType targetRoletype;
}
