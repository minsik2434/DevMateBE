package com.devmate.apiserver.dto.post.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentoringRequestDto extends RequestDto {
    @NotBlank
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "Pattern does not fit")
    private String phoneNumber;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String job;
    @NotNull
    private Integer career;
    @NotBlank
    private String githubUrl;
}
