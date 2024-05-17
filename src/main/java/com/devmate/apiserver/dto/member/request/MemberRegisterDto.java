package com.devmate.apiserver.dto.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberRegisterDto {
    @NotBlank(message = "Name NotBlank")
    private String name;
    @NotBlank(message = "LoginId NotBlank")
    private String loginId;
    @NotBlank(message = "Password NotBlank")
    private String password;
    @NotBlank(message = "Confirm Password NotBlank")
    private String confirmPassword;
    @NotBlank(message = "NickName NotBlank")
    private String nickName;
    @NotNull(message = "Experienced NotNull")
    private Boolean experienced;
    private List<String> interests;
}
