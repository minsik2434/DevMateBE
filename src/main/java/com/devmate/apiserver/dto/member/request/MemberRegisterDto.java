package com.devmate.apiserver.dto.member.request;

import com.devmate.apiserver.common.annotation.UniqueElements;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "사용자 이름", nullable = false, example = "최민식")
    private String name;

    @NotBlank(message = "LoginId NotBlank")
    @Schema(description = "사용자 아이디", nullable = false, example = "memberId")
    private String loginId;

    @NotBlank(message = "Password NotBlank")
    @Schema(description = "비밀번호", example = "memberPassword")
    private String password;

    @NotBlank(message = "Confirm Password NotBlank")
    @Schema(description = "비밀번호 확인", example = "memberPassword")
    private String confirmPassword;

    @NotBlank(message = "NickName NotBlank")
    @Schema(description = "닉네임", example = "민시기")
    private String nickName;

    @NotNull(message = "Experienced NotNull")
    @Schema(description = "경력유무", example = "true")
    private Boolean experienced;

    @NotNull(message = "Interests NotNull")
    @UniqueElements(message = "Interests Element Must Be Unique")
    @Schema(description = "관심 직종", example = "[1,2,3]")
    private List<Long> interests;
}
