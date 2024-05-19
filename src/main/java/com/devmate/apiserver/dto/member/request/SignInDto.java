package com.devmate.apiserver.dto.member.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {
    private String loginId;
    private String password;
}
