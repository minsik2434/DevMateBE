package com.devmate.apiserver.dto.post.request;

import com.devmate.apiserver.common.annotation.UniqueElements;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRegisterDto extends RegisterDto{
}
