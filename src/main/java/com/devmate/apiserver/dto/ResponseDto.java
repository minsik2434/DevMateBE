package com.devmate.apiserver.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
public abstract class ResponseDto<T> {
    private LocalDateTime responseTime;
    private Integer status;
    private T message;
    private String path;
}
