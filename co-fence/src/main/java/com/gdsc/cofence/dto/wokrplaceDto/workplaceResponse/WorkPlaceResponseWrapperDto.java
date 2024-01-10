package com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse;

import com.gdsc.cofence.dto.wokrplaceDto.WorkPlaceMetaDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WorkPlaceResponseWrapperDto {

    private WorkPlaceMetaDto meta;
    private List<WorkPlaceResponseDto> data;
}
