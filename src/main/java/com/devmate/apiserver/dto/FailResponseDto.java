package com.devmate.apiserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Setter
@Getter
public class FailResponseDto extends ResponseDto{
    private HttpStatus error;
    private String path;
    private String errorMessage;
}
