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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
    public ResponseEntity<SuccessResponseDto<PostDto>> studyRegister(Authentication authentication,
                                                @RequestBody @Validated StudyRegisterDto studyRegisterDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long postId = postService.postSave(loginId, "study", studyRegisterDto);
        PostDto postDto = postQueryService.postInfo(postId);
        SuccessResponseDto<PostDto> successResponse =
                controllerUtil.createSuccessResponse(postDto, HttpServletResponse.SC_OK);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }
    @PostMapping("/mentoring")
    public ResponseEntity<SuccessResponseDto<PostDto>> mentoringRegister(Authentication authentication,
                                                                              @RequestBody @Validated MentoringRegisterDto mentoringRegisterDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long postId = postService.postSave(loginId, "mentoring", mentoringRegisterDto);
        PostDto postDto = postQueryService.postInfo(postId);
        SuccessResponseDto<PostDto> successResponse =
                controllerUtil.createSuccessResponse(postDto, HttpServletResponse.SC_OK);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseDto<PostDto>> postInfoById(@PathVariable("id") Long postId){
        PostDto postDto = postQueryService.postInfo(postId);
        SuccessResponseDto<PostDto> successResponse =
                controllerUtil.createSuccessResponse(postDto, HttpServletResponse.SC_OK);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @GetMapping("/{category}/list")
    public ResponseEntity<SuccessResponseDto<Page<PostDto>>> postList(@PathVariable("category") String category,
                                                                      @RequestParam(value = "sort", defaultValue = "latest") String sort,
                                                                      @RequestParam(value = "sc" , required = false) String search,
                                                                      @RequestParam(value = "tag", defaultValue = "") String[] tags,
                                                                      @RequestParam(value = "page", defaultValue = "0") int page){
        Page<PostDto> postDtos = postQueryService.postsFilterParam(category, sort, search, tags, page);
        SuccessResponseDto<Page<PostDto>> successResponse =
                controllerUtil.createSuccessResponse(postDtos, HttpServletResponse.SC_OK);
        return ResponseEntity.ok(successResponse);
    }

}
