package com.gdsc.cofence.controller.workplace;

import com.gdsc.cofence.dto.userDto.AttendanceUserInfoDto;
import com.gdsc.cofence.dto.userDto.UserInfoDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceRequest.WorkPlacePagingAmountRequestDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.UserRegistrationResponseDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.WorkPlaceResponseDto;
import com.gdsc.cofence.dto.wokrplaceDto.workplaceResponse.WorkPlaceResponseWrapperDto;
import com.gdsc.cofence.service.workplace.WorkPlaceService;
import com.gdsc.cofence.service.workplace.WorkplaceRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "작업 현장")
@RequestMapping("/api/v22/wp")
public class WorkplaceController {

    private final WorkPlaceService workPlaceService;
    private final WorkplaceRegistrationService workplaceRegistrationService;

    @PostMapping("/checkIn/{workplaceId}")
    @Operation(summary = "작업현장으로 출근합니다", description = "workplaceId를 PathVariable로 입력받고, accessToken을 통해서 해당 사용자에 대한 Id를 추출해서 해당 작업현장으로 출근하는 기능")
    public ResponseEntity<UserRegistrationResponseDto> checkInWorkplace(@PathVariable Long workplaceId, Principal principal) {
        UserRegistrationResponseDto attendanceDto = workplaceRegistrationService.checkInWorkplace(workplaceId, principal);

        return ResponseEntity.ok(attendanceDto);
    }

    @GetMapping("/getInfo")
    @Operation(summary = "작업현장을 불러옵니다", description = "불러올 페이지를 정하고 몇개의 데이터를 받아올지 파라미터로 받고, accessToken Authorization에 accessToken을 받고 검증이 완료되면 반환")
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

    @GetMapping("/attendances/{workplaceId}")
    @Operation(summary = "해당 작업현장에 출근한 사람들 조회[테스트용]", description = "해당 작업현장에 출근한 사람들의 목록을 조회한다[테스트용]")
    public ResponseEntity<List<AttendanceUserInfoDto>> getAttendeesInWorkplace(@PathVariable Long workplaceId, Principal principal) {
        List<AttendanceUserInfoDto> attendees = workplaceRegistrationService.getAttendeesInWorkplace(workplaceId, principal);

        return ResponseEntity.ok(attendees);
    }

    @DeleteMapping("/{workplaceId}/checkout")
    @Operation(summary = "해당 작업현장 탈퇴", description = "해당 작업현장 Id와 accessToken을 함께 받아서 accessToken에 해당하는 사용자는 해당 작업현장에서 탈퇴처리 된다")
    public ResponseEntity<String> checkOutWorkplace(@PathVariable Long workplaceId, Principal principal) {
        String message = workplaceRegistrationService.checkOutWorkplace(workplaceId, principal);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
