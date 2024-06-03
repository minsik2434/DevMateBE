package com.devmate.apiserver.dto.post.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudyRegisterDto extends RegisterDto{
    @Min(1)
    @Max(4)
    @NotNull(message = "recruitCount Not Null")
    private Integer recruitCount;
    @NotBlank(message = "Proceed Not Blank")
    private String proceed;
    @NotNull(message = "deadLine Not Null")
    private LocalDateTime deadLine;
}
