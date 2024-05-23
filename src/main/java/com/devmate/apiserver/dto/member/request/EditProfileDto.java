package com.devmate.apiserver.dto.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EditProfileDto {
    @NotBlank(message = "Name NotBlank")
    private String name;
    @NotBlank(message = "NickName NotBlank")
    private String nickName;
    @NotBlank(message = "ImgUrl NotBlank")
    private String imgUrl;
    @NotNull(message = "Experienced NotNull")
    private Boolean experienced;
    @NotNull(message = "Interests NotNull")
    private List<Long> interests;
}
