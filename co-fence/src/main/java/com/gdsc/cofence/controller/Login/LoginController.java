package com.gdsc.cofence.controller.login;

import com.gdsc.cofence.dto.tokenDto.RenewAccessTokenDto;
import com.gdsc.cofence.dto.userDto.UserEmailDto;
import com.gdsc.cofence.dto.userDto.UserInfoDto;
import com.gdsc.cofence.dto.userDto.userRequest.UserAndTokenResponseDto;
import com.gdsc.cofence.service.login.TokenRenewService;
import com.gdsc.cofence.service.login.UserLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "인증")
@RequestMapping("/api/v22/auth")
public class LoginController {

    private final UserLoginService userLoginService;
    private final TokenRenewService tokenRenewService;

    @PostMapping("/checkEmail")
    @Operation(summary = "중복회원 검증", description = "google, kakao를 통해서 사전적으로 받은 email을 DB조회를 통해서 중복회원 여부를 검사해서 true 혹은 false를 반환합니다")
    public boolean checkEmailExistence(@RequestParam String email) {
        return userLoginService.duplicateInspectionEmail(email);
    }

    @PostMapping("/signUp")
    @Operation(summary = "회원가입", description = "사용자 정보를 받고 DB에 저장하고 refreshToken, accessToken을 발급해서 회원가입을 진행하고 사용자 정보와 함께 반환합니다.")
    public ResponseEntity<UserAndTokenResponseDto> signUp(@RequestBody UserInfoDto userInfoDto) {
        UserAndTokenResponseDto userData = userLoginService.SignUp(userInfoDto);

        return ResponseEntity.ok(userData);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 email을 받아서 해당 사용자를 찾고 갱신된 accessToken, refreshToken 사용자 정보와 함께 반환합니다.")
    public ResponseEntity<UserAndTokenResponseDto> login(@RequestBody UserEmailDto userEmailDto) {
        UserAndTokenResponseDto refreshUserData = userLoginService.login(userEmailDto);

        return ResponseEntity.ok(refreshUserData);
    }

    @PostMapping("/renew")
    @Operation(summary = "엑세스 토큰 갱신", description = "refreshToken을 통해서 accessToken을 갱신합니다")
    public ResponseEntity<RenewAccessTokenDto> renewAccessToken(@RequestHeader("Authorization") String refreshToken) {

//        if (refreshToken.startsWith("Bearer")) {       -> 추후에 Bearer토큰일 경우
//            refreshToken = refreshToken.substring(7);
//        }

        RenewAccessTokenDto renewAccessTokenDto = tokenRenewService.renewAccessToken(refreshToken);

        return new ResponseEntity<>(renewAccessTokenDto, HttpStatus.OK);
    }
}
