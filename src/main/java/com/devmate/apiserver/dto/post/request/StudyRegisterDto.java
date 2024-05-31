package com.devmate.apiserver.dto.post.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudyRegisterDto extends RegisterDto{
    private Integer recruitCount;
    private String proceed;
    private LocalDateTime deadLine;
}
