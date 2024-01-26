package com.gdsc.cofence.controller.communicate;


import com.gdsc.cofence.dto.userDto.userResponse.UserListResponseDto;
import com.gdsc.cofence.service.communicate.UserCommunicateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "근로자/관리자")
@RequestMapping("/api/v22/communicate")
public class UserCommunicateController {

    private final UserCommunicateService userCommunicateService;

    @GetMapping("/list")
    @Operation(summary = "근로자, 관리자 리스트를 불러옵니다", description = "accessToken을 통해서 인증객체에서 정보를 추출해서 해당 작업현장의 근로자/관리자 List를 불러옵니다. ")
    public ResponseEntity<List<UserListResponseDto>> getUserList(Principal principal) {
        List<UserListResponseDto> result = userCommunicateService.getWorkplaceUserInfoList(principal);

        return ResponseEntity.ok(result);
    }
}