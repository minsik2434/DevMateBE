package com.devmate.apiserver.controller;

import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.dto.ImageDeleteDto;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.service.ImageUploadService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
@Slf4j
public class ImageUploadController {

    private final ImageUploadService imageUploadService;
    private final ControllerUtil controllerUtil;
    @PostMapping("/upload")
    public ResponseEntity<SuccessResponseDto<String>> saveImage(@RequestParam("image") MultipartFile file) throws IOException {
        String url = imageUploadService.upload(file);
        SuccessResponseDto<String> successResponse =
                controllerUtil.createSuccessResponse(url, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteImage(@RequestBody ImageDeleteDto imageDeleteDto){
        imageUploadService.deleteImageFromS3(imageDeleteDto.getImageUrl());
        return ResponseEntity.noContent().build();
    }
}
