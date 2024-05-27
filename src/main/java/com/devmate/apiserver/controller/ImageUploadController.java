package com.devmate.apiserver.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.devmate.apiserver.dto.img.request.ImageRegisterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
@Slf4j
public class ImageUploadController {

    @PostMapping("/upload")
    public String saveImage(@RequestParam("image") MultipartFile file) throws IOException {
        log.info("{}",file);
        return "test";
    }
}
