package com.devmate.apiserver.controller;

import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.post.request.MentoringRegisterDto;
import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import com.devmate.apiserver.dto.post.request.StudyRegisterDto;
import com.devmate.apiserver.dto.post.response.MentoringDto;
import com.devmate.apiserver.dto.post.response.PostDto;
import com.devmate.apiserver.dto.post.response.StudyDto;
import com.devmate.apiserver.service.post.PostQueryService;
import com.devmate.apiserver.service.post.PostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final ControllerUtil controllerUtil;
    private final PostService postService;
    private final PostQueryService postQueryService;
    @PostMapping("/{category}")
    public ResponseEntity<SuccessResponseDto<PostDto>> postRegister(@PathVariable("category") String category,
                                                           Authentication authentication,
                                                           @RequestBody @Validated PostRegisterDto postRegisterDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long postId = postService.postSave(loginId,category, postRegisterDto);
        PostDto postDto = postQueryService.postInfo(postId);
        SuccessResponseDto<PostDto> successResponse =
                controllerUtil.createSuccessResponse(postDto, HttpServletResponse.SC_OK);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }
    @PostMapping("/study")
    public ResponseEntity<SuccessResponseDto<StudyDto>> studyRegister(Authentication authentication,
                                                @RequestBody StudyRegisterDto studyRegisterDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long postId = postService.postSave(loginId, "study", studyRegisterDto);
        StudyDto studyDto = postQueryService.studyInfo(postId);
        SuccessResponseDto<StudyDto> successResponse =
                controllerUtil.createSuccessResponse(studyDto, HttpServletResponse.SC_OK);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }
    @PostMapping("/mentoring")
    public ResponseEntity<SuccessResponseDto<MentoringDto>> mentoringRegister(Authentication authentication,
                                                                              @RequestBody MentoringRegisterDto mentoringRegisterDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long postId = postService.postSave(loginId, "mentoring", mentoringRegisterDto);
        MentoringDto mentoringDto = postQueryService.mentoringInfo(postId);
        SuccessResponseDto<MentoringDto> successResponse =
                controllerUtil.createSuccessResponse(mentoringDto, HttpServletResponse.SC_OK);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }
}
