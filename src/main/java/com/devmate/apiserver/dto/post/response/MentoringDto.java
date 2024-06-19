package com.devmate.apiserver.dto.post.response;

import com.devmate.apiserver.domain.Mento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentoringDto extends PostDto{
    private String job;
    private String phoneNumber;
    private Integer career;
    private String email;
    private String githubUrl;
    public MentoringDto(Mento mento) {
        super(mento);
        this.job = mento.getJob();
        this.email = mento.getEmail();
        this.phoneNumber = mento.getPhoneNumber();
        this.career = mento.getCareer();
        this.githubUrl = mento.getGithubUrl();
    }
}
