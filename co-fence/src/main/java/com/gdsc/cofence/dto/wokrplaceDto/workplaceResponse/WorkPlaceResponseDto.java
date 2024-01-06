package com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse;

import com.gdsc.cofence.entity.workplaceManagement.WorkPlace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlaceResponseDto {

    private Long workPlaceId;
    private String workPlaceName;
    private String workPlaceAddress;

    @Builder
    public WorkPlaceResponseDto(WorkPlace workPlace) {
        this.workPlaceId = workPlace.getWorkplaceId();
        this.workPlaceName = workPlace.getWorkplaceName();
        this.workPlaceAddress = workPlace.getWorkplaceAddress();
    }
}
