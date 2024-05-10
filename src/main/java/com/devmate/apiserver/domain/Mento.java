package com.devmate.apiserver.domain;

import jakarta.persistence.Entity;

@Entity
public class Mento extends Post{
    private String job;
    private Integer career;
    private Integer price;
    private Integer mentiMaxCount;
    private Integer mentoringTime;
    private String githubUrl;

}
