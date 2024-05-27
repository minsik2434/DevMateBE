package com.devmate.apiserver.dto.img.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class ImageRegisterDto {
    private MultipartFile file;
    private String name;
}
