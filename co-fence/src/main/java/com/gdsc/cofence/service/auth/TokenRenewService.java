package com.gdsc.cofence.service.auth;

import com.gdsc.cofence.dto.tokenDto.RenewAccessTokenDto;
import com.gdsc.cofence.dto.tokenDto.TokenParsing.UserAccessTokenParsingDto;
import com.gdsc.cofence.dto.tokenDto.TokenParsing.UserRefreshTokenParsingDto;
import com.gdsc.cofence.entity.user.RoleType;
import com.gdsc.cofence.entity.user.User;
import com.gdsc.cofence.exception.ErrorCode;
import com.gdsc.cofence.exception.model.CustomException;
import com.gdsc.cofence.jwt.TokenProvider;
import com.gdsc.cofence.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenRenewService {

    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;


    // 갱신된 accessToken을 반환하는 로직
    @Transactional
    public RenewAccessTokenDto renewAccessToken(String refreshToken) {

        if (!tokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN_EXCEPTION, ErrorCode.INVALID_TOKEN_EXCEPTION.getMessage());
        }

        // 사용자 Id추출
        UserRefreshTokenParsingDto userSeqDto = getUserFromRefreshToken(refreshToken);

        User user = userRepository.findByEmail(userSeqDto.getUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EMAIL_EXCEPTION,
                        ErrorCode.NOT_FOUND_EMAIL_EXCEPTION.getMessage())); // 해당하는 사용자의 이메일을 찾을 수 없는 경우

        String newAccessToken;

        try {
            newAccessToken = tokenProvider.createAccessToken(user);
        } catch (Exception e) { // accessToken을 생성하는 과정에서 생기는 문제에 대한 예외처리(알고리즘으로 암호화, key문제)
            throw new CustomException(ErrorCode.TOKEN_CREATION_EXCEPTION, ErrorCode.TOKEN_CREATION_EXCEPTION.getMessage());
        }

        return RenewAccessTokenDto.builder()
                .renewAccessToken(newAccessToken)
                .build();
    }


    // accessToken -> 파싱
    @Transactional
    public UserAccessTokenParsingDto getUserFromAccessToken(String accessToken) {
        Claims claims = tokenProvider.getClaimsFromToken(accessToken);

        Long userId = Long.parseLong(claims.getSubject());
        String role = (String) claims.get("Role");
        String email = (String) claims.get("email");

        return UserAccessTokenParsingDto.builder()
                .userSeq(userId)
                .roleType(RoleType.valueOf(role))
                .email(email)
                .build();
    }

    // refreshToken -> 파싱
    @Transactional
    public UserRefreshTokenParsingDto getUserFromRefreshToken(String refreshToken) {
        Claims claims = tokenProvider.getClaimsFromToken(refreshToken);

        String userEmail = (String) claims.get("email");

        return UserRefreshTokenParsingDto.builder()
                .userEmail(userEmail)
                .build();
    }
}
