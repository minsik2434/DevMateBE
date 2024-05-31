package com.devmate.apiserver.dto.post.request;

import com.devmate.apiserver.common.annotation.UniqueElements;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class RegisterDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @UniqueElements
    @Size(max = 4, message = "Size must be 4 or less")
    private List<String> tags;
}
