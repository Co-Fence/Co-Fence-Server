package com.gdsc.cofence.controller.Login;

import com.gdsc.cofence.dto.tokenDto.TokenParsing.UserAccessTokenParsingDto;
import com.gdsc.cofence.dto.tokenDto.TokenParsing.UserRefreshTokenParsingDto;
import com.gdsc.cofence.service.login.TokenRenewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/parsing")
public class TokenController {

    private final TokenRenewService tokenRenewService;

    @GetMapping("/userInfo") // accessToken을 파싱해서 사용자 검증을 위한 정보를 받는 api
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

    @GetMapping("/refresh/userInfo") // refreshToken을 파싱해서 userSeq를 받는 api
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
