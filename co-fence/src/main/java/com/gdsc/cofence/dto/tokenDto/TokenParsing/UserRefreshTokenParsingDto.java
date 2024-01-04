package com.gdsc.cofence.dto.tokenDto.TokenParsing;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRefreshTokenParsingDto {

    private String userEmail;
}
