package com.gdsc.cofence.dto.tokenDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RenewAccessTokenDto {

    private String renewAccessToken;
}
