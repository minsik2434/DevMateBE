package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.post.request.MentoringRegisterDto;
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

}
