package com.devmate.apiserver.dto.post.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRegisterDto {
    private String title;
    private String content;
    private List<String> tags;
}
