package com.devmate.apiserver.dto.member.request;

import com.devmate.apiserver.common.annotation.UniqueElements;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @NotNull(message = "Interests NotNull")
    @UniqueElements(message = "Interests Element Must Be Unique")
    private List<Long> interests;
}
