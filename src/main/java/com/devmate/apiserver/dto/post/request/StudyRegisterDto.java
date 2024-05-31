package com.devmate.apiserver.dto.post.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudyRegisterDto extends RegisterDto{
    @Min(1)
    @Max(4)
    private Integer recruitCount;
    @NotBlank
    private String proceed;
    @NotBlank
    private LocalDateTime deadLine;
}
