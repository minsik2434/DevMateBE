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
    @NotBlank(message = "phoneNumber not Blank")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "phoneNumber Pattern does not fit")
    private String phoneNumber;
    @NotBlank(message = "email Not Blank")
    @Email(message = "email pattern does not fit")
    private String email;
    @NotBlank(message = "job not Blank")
    private String job;
    @NotNull(message = "career not null")
    private Integer career;
    @NotBlank(message = "githubUrl not Blank")
    private String githubUrl;
}
