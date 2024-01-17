package com.gdsc.cofence.dto.userDto.userResponse;

import com.gdsc.cofence.dto.wokrplaceDto.WorkplaceIdDto;
import com.nimbusds.jose.shaded.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserAndTokenResponseDto {

    private String name;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;
    private String nationality;
    private String roleType;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("refresh_token")
    private String refreshToken;
    private Long workplaceId;
}
