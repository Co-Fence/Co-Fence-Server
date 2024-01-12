package com.gdsc.cofence.controller.workplace;

import com.gdsc.cofence.dto.wokrplaceDto.workplaceRequest.WorkPlaceNameDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceRequest.WorkPlacePagingAmountRequestDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.WorkPlaceResponseDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.WorkPlaceResponseWrapperDto;
import com.gdsc.cofence.service.workplace.WorkPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/searchByName")
    @Operation(summary = "작업현장을 이름으로 검색", description = "작업현장 이름을 파라미터값으로 받고 인증과정을 거치면 해당 작업현장이름의 키워드가 들어간 작업현장 목록들을 반환")
    public ResponseEntity<Page<WorkPlaceResponseDto>> searchWorkPlacesByName(@RequestParam String keyword, // -> workplaceName 임
                                                                             Pageable pageable,
                                                                             Principal principal) {

        Page<WorkPlaceResponseDto> workPlaces = workPlaceService.searchWorkPlaceByName(keyword, pageable, principal);
        return new ResponseEntity<>(workPlaces, HttpStatus.OK);
    }

    @GetMapping("/searchById")
    @Operation(summary = "작업현장을 Id로 검색", description = "작업현장 Id를 파라미터값으로 받고 인증과정을 거친 사용자면 해당 현장정보를 가져온다")
    public ResponseEntity<WorkPlaceResponseDto> searchWorkPlaceById(@RequestParam Long id, Principal principal) {

        WorkPlaceResponseDto workPlaceData = workPlaceService.searchWorkPlaceById(id, principal);

        return ResponseEntity.ok(workPlaceData);
    }
}
