package com.devmate.apiserver.dto.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDto {
    @NotBlank(message = "loginId not Blank")
    @Schema(description = "사용자 아이디", nullable = false, example = "memberId")
    private String loginId;
    @NotBlank(message = "password not Blank")
    @Schema(description = "사용자 비밀번호", nullable = false, example = "memberPassword")
    private String password;
}
