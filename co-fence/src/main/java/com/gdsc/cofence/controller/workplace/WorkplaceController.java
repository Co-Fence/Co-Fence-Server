package com.gdsc.cofence.controller.workplace;

import com.gdsc.cofence.dto.wokrplaceDto.workplaceRequest.WorkPlacePagingAmountRequestDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.WorkPlaceResponseWrapperDto;
import com.gdsc.cofence.service.workplace.WorkPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
@Tag(name = "작업 현장")
@RequestMapping("/api/v22/wp")
public class WorkplaceController {

    private final WorkPlaceService workPlaceService;


    @GetMapping("/getInfo")
    @Operation(summary = "작업현장을 불러옵니다", description = "불러올 페이지를 정하고 몇개의 데이터를 받아올지 파라미터로 받고, accessToken을 Authorization에 accessToken을 받고 검증이 완료되면 반환")
    public ResponseEntity<WorkPlaceResponseWrapperDto> getWorkPlaces(@ModelAttribute WorkPlacePagingAmountRequestDto requestDto, Principal principal) {
        WorkPlaceResponseWrapperDto data = workPlaceService.getWorkPlaces(requestDto, principal);

        return ResponseEntity.ok(data);
    }
}
