package com.gdsc.cofence.dto.wokrplaceDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class WorkplaceIdDto {

    private Long workplaceId;
    private LocalDateTime checkInTime;
}
