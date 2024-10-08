package com.devmate.apiserver.dto.comment.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentRequestDto {
    @NotBlank(message = "Comment Not Blank")
    private String comment;
}
