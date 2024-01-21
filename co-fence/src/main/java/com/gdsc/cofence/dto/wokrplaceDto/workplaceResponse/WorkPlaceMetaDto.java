package com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkPlaceMetaDto {

    private int count;
    private int page;
    private Boolean hasMore;
}
