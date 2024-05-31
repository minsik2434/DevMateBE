package com.devmate.apiserver.controller;

import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import com.devmate.apiserver.dto.post.request.StudyRegisterDto;
import com.devmate.apiserver.dto.post.response.PostDto;
import com.devmate.apiserver.service.post.PostQueryService;
import com.devmate.apiserver.service.post.PostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return ResponseEntity.ok(successResponse);
    }
}
