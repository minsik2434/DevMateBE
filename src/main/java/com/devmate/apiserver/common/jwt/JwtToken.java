package com.devmate.apiserver.common.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class JwtToken {
    @Schema(description = "GrantType", example = "예) Bearer")
    private String grantType;
    @Schema(description = "AccessToken", example = "예) 123123.abcderghijklnm=+")
    private String accessToken;
    @Schema(description = "refreshToken", example = "예) 123123.abcderghijklnm=+")
    private String refreshToken;
}
