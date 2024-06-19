package com.devmate.apiserver.controller;

import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.dto.FailResponseDto;
import com.devmate.apiserver.dto.ImageDeleteDto;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.service.ImageUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "이미지 업로드", description = "이미지 업로드 후 이미지 URL 반환 인증 헤더 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "이미지 업로드 성공", content = @Content(schema = @Schema(example = "https://img.test.com"))),
            @ApiResponse(responseCode = "401", description = "인증 에러", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @PostMapping("/upload")
    public ResponseEntity<SuccessResponseDto<String>> saveImage(@RequestParam("image") MultipartFile file) {
        String url = imageUploadService.upload(file);
        SuccessResponseDto<String> successResponse =
                controllerUtil.createSuccessResponse(url, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }

    @Operation(summary = "이미지 삭제", description = "업로드된 이미지 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "댓글 삭제 성공", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "401", description = "인증 에러", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteImage(@RequestBody ImageDeleteDto imageDeleteDto){
        imageUploadService.deleteImageFromS3(imageDeleteDto.getImageUrl());
        return ResponseEntity.noContent().build();
    }
}
