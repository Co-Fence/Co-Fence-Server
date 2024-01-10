package com.gdsc.cofence.dto.wokrplaceDto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkPlaceMeta {

    private int count;
    private Boolean hasMore;
}
