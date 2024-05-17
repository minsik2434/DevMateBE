package com.devmate.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Setter
@Getter
public class ValidFailResponseDto extends ResponseDto<String>{
    private HttpStatus error;
}
