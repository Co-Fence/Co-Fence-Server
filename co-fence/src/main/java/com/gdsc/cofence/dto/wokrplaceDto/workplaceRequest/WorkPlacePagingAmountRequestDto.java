package com.gdsc.cofence.dto.wokrplaceDto.workplaceRequest;

import lombok.Data;

@Data
public class WorkPlacePagingAmountRequestDto {

    private int page; // 조회하고자 하는 페이지
    private int size; // 한 페이지에 보여줄 개수
}
