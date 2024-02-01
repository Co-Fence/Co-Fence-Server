package com.gdsc.cofence.dto.noticeDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeMetaDto {

    private int count;
    private int page;
    private Boolean hasMore;
}
