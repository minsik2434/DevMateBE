package com.devmate.apiserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public abstract class ResponseDto {
    @Schema(description = "반환 시간", example = "예) 2024-05-23T09:19:05.233Z")
    private LocalDateTime responseTime;
    @Schema(description = "상태 코드")
    private Integer status;
}
