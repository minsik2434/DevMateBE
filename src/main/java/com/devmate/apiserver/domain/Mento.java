package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.post.request.MentoringRequestDto;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Mento extends Post{
    private String job;
    private String phoneNumber;
    private Integer career;
    private String githubUrl;
    private String email;


    public Mento(Member member, MentoringRequestDto mentoringRegisterDto){
        super(member, mentoringRegisterDto);
        this.job = mentoringRegisterDto.getJob();
        this.phoneNumber = mentoringRegisterDto.getPhoneNumber();
        this.career = mentoringRegisterDto.getCareer();
        this.githubUrl = mentoringRegisterDto.getGithubUrl();
        this.email = mentoringRegisterDto.getEmail();
    }

    public void editMento(MentoringRequestDto mentoringEditDto){
        super.editPost(mentoringEditDto);
        this.job = mentoringEditDto.getJob();
        this.phoneNumber = mentoringEditDto.getPhoneNumber();
        this.career = mentoringEditDto.getCareer();
        this.githubUrl = mentoringEditDto.getGithubUrl();
        this.email = mentoringEditDto.getEmail();
    }
}
