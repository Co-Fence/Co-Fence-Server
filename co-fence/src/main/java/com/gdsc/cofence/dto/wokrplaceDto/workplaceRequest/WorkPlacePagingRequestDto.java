package com.gdsc.cofence.dto.wokrplaceDto.workplaceRequest;

import lombok.Data;

@Data
public class WorkPlacePagingRequestDto {

    private Long workplaceId;
    private String workplaceName;
    private String workplaceAddress;
}
