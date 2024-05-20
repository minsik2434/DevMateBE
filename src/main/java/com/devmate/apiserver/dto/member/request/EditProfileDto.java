package com.devmate.apiserver.dto.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EditProfileDto {
    //TODO Interests 변경 추가
    @NotBlank(message = "Name NotBlank")
    private String name;
    @NotBlank(message = "NickName NotBlank")
    private String nickName;
    @NotBlank(message = "ImgUrl NotBlank")
    private String imgUrl;
    @NotNull(message = "Experienced NotNull")
    private Boolean experienced;
}
