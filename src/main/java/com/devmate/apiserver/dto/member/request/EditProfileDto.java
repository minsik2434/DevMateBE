package com.devmate.apiserver.dto.member.request;

import com.devmate.apiserver.common.annotation.UniqueElements;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class EditProfileDto {
    @NotBlank(message = "Name NotBlank")
    @Schema(description = "사용자 이름", nullable = false, example = "최민식")
    private String name;

    @NotBlank(message = "NickName NotBlank")
    @Schema(description = "닉네임", nullable = false, example = "민시기")
    private String nickName;

    @Schema(description = "닉네임", nullable = false, example = "test")
    @NotBlank(message = "ImgUrl NotBlank")
    private String imgUrl;

    @NotNull(message = "Experienced NotNull")
    @Schema(description = "경력유무", nullable = false, example = "true")
    private Boolean experienced;

    @NotNull(message = "Interests NotNull")
    @UniqueElements(message = "Interests Element Must Be Unique")
    @Schema(description = "관심 직종", nullable = false, example = "[1,2,3]")
    private List<Long> interests;
}
