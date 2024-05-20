package com.devmate.apiserver.dto.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {
    @NotBlank(message = "loginId not Blank")
    private String loginId;
    @NotBlank(message = "password not Blank")
    private String password;
}
