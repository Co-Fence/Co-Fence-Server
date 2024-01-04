package com.gdsc.cofence.controller.Login;

import com.gdsc.cofence.dto.tokenDto.TokenParsing.UserAccessTokenParsingDto;
import com.gdsc.cofence.dto.tokenDto.TokenParsing.UserRefreshTokenParsingDto;
import com.gdsc.cofence.service.login.TokenRenewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Tag(name = "파싱")
@RequestMapping("/api/v1/parsing")
public class TokenController {

    private final TokenRenewService tokenRenewService;

    @GetMapping("/accessParsing")
    @Operation(summary = "accessToken 파싱", description = "accessToken을 파싱해서 사용자 정보를 반환합니다")
    public ResponseEntity<UserAccessTokenParsingDto> getUserInfo(@RequestHeader("Authorization") String accessToken) {
        UserAccessTokenParsingDto userData = tokenRenewService.getUserFromAccessToken(accessToken);
        return ResponseEntity.ok(userData);
    }
    /* -> 이런식으로 받게 됨
        {
            "userSeq": 1,
            "roleType": "ROLE_ADMIN",
            "email": "hong@google.com"
        }
    */

    @GetMapping("/refreshParsing")
    @Operation(summary = "refreshToken 파싱", description = "refreshToken을 파싱해서 사용자 Id를 반환합니다.")
    public ResponseEntity<UserRefreshTokenParsingDto> getRefreshUserInfo(@RequestHeader("Authorization") String refreshToken) {
        UserRefreshTokenParsingDto userId = tokenRenewService.getUserFromRefreshToken(refreshToken);
        return ResponseEntity.ok(userId);
    }

    /* -> 이런식으로 받게 됨
        {
            "userSeq": 1
        }
    */

}
