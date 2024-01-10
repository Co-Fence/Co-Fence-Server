package com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse;

import com.gdsc.cofence.dto.wokrplaceDto.WorkPlaceMeta;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WorkPlaceResponseWrapperDto {

    private WorkPlaceMeta meta;
    private List<WorkPlaceResponseDto> data;
}
