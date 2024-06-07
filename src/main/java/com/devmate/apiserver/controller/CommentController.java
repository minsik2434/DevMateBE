package com.devmate.apiserver.controller;

import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.comment.request.CommentRegisterDto;
import com.devmate.apiserver.dto.comment.response.CommentDto;
import com.devmate.apiserver.service.CommentQueryService;
import com.devmate.apiserver.service.CommentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/comments")
@Slf4j
@RestController
public class CommentController {
    private final CommentService commentService;
    private final CommentQueryService commentQueryService;
    private final ControllerUtil controllerUtil;
    @PostMapping("/{postId}")
    public ResponseEntity<SuccessResponseDto<CommentDto>> commentRegister(Authentication authentication,
                                                                          @PathVariable("postId") Long postId,
                                                                          @RequestBody @Validated CommentRegisterDto commentRegisterDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long commentId = commentService.commentSave(loginId, postId, commentRegisterDto);
        CommentDto commentDto = commentQueryService.getCommentDto(commentId);
        SuccessResponseDto<CommentDto> successResponse =
                controllerUtil.createSuccessResponse(commentDto, HttpServletResponse.SC_OK);
        return ResponseEntity.ok(successResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<SuccessResponseDto<List<CommentDto>>> commentList(@PathVariable("postId") Long postId){
        List<CommentDto> commentList = commentQueryService.getCommentList(postId);
        SuccessResponseDto<List<CommentDto>> successResponse =
                controllerUtil.createSuccessResponse(commentList, HttpServletResponse.SC_OK);
        return ResponseEntity.ok(successResponse);
    }
}
