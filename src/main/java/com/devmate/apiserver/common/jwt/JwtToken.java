package com.devmate.apiserver.common.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
