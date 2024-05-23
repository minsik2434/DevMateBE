package com.devmate.apiserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SuccessResponseDto<T> extends ResponseDto{
    @Schema(description = "데이터", example = "예) {...}")
    private T data;
}
