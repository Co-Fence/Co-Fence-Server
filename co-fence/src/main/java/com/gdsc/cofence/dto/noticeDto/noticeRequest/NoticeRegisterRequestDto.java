package com.gdsc.cofence.dto.noticeDto.noticeRequest;

import com.gdsc.cofence.entity.user.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
public class NoticeRegisterRequestDto {

    private String noticeSubject;
    private RoleType targetRoleType;
    private String noticeDetail;
    private List<String> noticeImageUrls;
    private Long workplaceId;
}
