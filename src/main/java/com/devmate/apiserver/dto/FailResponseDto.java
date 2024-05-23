package com.devmate.apiserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Setter
@Getter
public class FailResponseDto extends ResponseDto{
    @Schema(description = "요청 상태", example = "예) BAD_REQUEST")
    private HttpStatus error;
    @Schema(description = "요청 경로", example = "예) /member/test")
    private String path;
    @Schema(description = "메시지", example = "예) is exists LoginId")
    private String errorMessage;
}
