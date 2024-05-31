package com.devmate.apiserver.dto.post.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentoringRegisterDto extends RegisterDto{
    private String phoneNumber;
    private String email;
    private String job;
    private Integer career;
    private String githubUrl;
}
