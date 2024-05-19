package com.devmate.apiserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SuccessResponseDto<T> extends ResponseDto{
    private T data;
}
