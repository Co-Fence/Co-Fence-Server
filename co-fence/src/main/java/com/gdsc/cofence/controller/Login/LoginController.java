package com.gdsc.cofence.controller.Login;

import com.gdsc.cofence.dto.tokenDto.RenewAccessTokenDto;
import com.gdsc.cofence.dto.userDto.UserEmailDto;
import com.gdsc.cofence.dto.userDto.UserInfo;
import com.gdsc.cofence.dto.userDto.userRequest.UserAndTokenResponseDto;
import com.gdsc.cofence.service.login.TokenRenewService;
import com.gdsc.cofence.service.login.UserLoginService;
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
@RequestMapping("/api/auth")
public class LoginController {

    private final UserLoginService userLoginService;
    private final TokenRenewService tokenRenewService;

    @PostMapping("/checkEmail") // 이메일이 이미 데이터베이스에 있는지에 대한 여부를 반환하는 api
    public boolean checkEmailExistence(@RequestParam String email) {
        return userLoginService.duplicateInspectionEmail(email);
    }

    @PostMapping("/signUp") // 처음 회원가입 할 때 사용할 api
    public ResponseEntity<UserAndTokenResponseDto> signUp(@RequestBody UserInfo userInfo) {
        UserAndTokenResponseDto userData = userLoginService.SignUp(userInfo);

        return ResponseEntity.ok(userData);
    }

    @PostMapping("/login") // 회원가입 후 로그인 할 때 사용할 api -> email을 json형태로 받아서 새로 갱신된(refreshToken, accessToken) + 사용자 정보와 함께 반환한다.
    public ResponseEntity<UserAndTokenResponseDto> login(@RequestBody UserEmailDto userEmailDto) {
        UserAndTokenResponseDto refreshUserData = userLoginService.login(userEmailDto);

        return ResponseEntity.ok(refreshUserData);
    }


    @PostMapping("/renew") // -> login api를 통해서 갱신된 refreshToken, accessToken을 발급받기에 이 api는 딱히 필요가 없을것으로 보임
    public ResponseEntity<RenewAccessTokenDto> renewAccessToken(@RequestHeader("Authorization") String refreshToken) {

//        if (refreshToken.startsWith("Bearer")) {       -> 추후에 Bearer토큰일 경우
//            refreshToken = refreshToken.substring(7);
//        }

        RenewAccessTokenDto renewAccessTokenDto = tokenRenewService.renewAccessToken(refreshToken);

        return new ResponseEntity<>(renewAccessTokenDto, HttpStatus.OK);
    }
}
