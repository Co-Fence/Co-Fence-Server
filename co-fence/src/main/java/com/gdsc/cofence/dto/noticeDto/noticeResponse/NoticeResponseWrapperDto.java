package com.gdsc.cofence.dto.noticeDto.noticeResponse;

import com.gdsc.cofence.dto.noticeDto.NoticeMetaDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NoticeResponseWrapperDto {

    private NoticeMetaDto meta;
    private List<NoticeSearchResponseDto> data;
}
