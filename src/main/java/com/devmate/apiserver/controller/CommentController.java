package com.devmate.apiserver.controller;

import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.dto.FailResponseDto;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.comment.request.CommentRequestDto;
import com.devmate.apiserver.dto.comment.response.CommentDto;
import com.devmate.apiserver.dto.good.GoodDto;
import com.devmate.apiserver.service.CommentQueryService;
import com.devmate.apiserver.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "댓글 컨트롤러", description = "댓글 API")
@Slf4j
@RestController
public class CommentController {
    private final CommentService commentService;
    private final CommentQueryService commentQueryService;
    private final ControllerUtil controllerUtil;

    @Operation(summary = "댓글 생성", description = "게시글에 회원의 댓글 추가, 인증 토큰 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "댓글 등록 성공", content = @Content(schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "값 검증 에러", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @PostMapping("/{postId}")
    public ResponseEntity<SuccessResponseDto<CommentDto>> commentRegister(Authentication authentication,
                                                                          @PathVariable("postId") Long postId,
                                                                          @RequestBody @Validated CommentRequestDto commentRequestDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long commentId = commentService.commentSave(loginId, postId, commentRequestDto);
        CommentDto commentDto = commentQueryService.getCommentDto(commentId);
        SuccessResponseDto<CommentDto> successResponse =
                controllerUtil.createSuccessResponse(commentDto, HttpServletResponse.SC_CREATED);
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(successResponse);
    }

    @Operation(summary = "댓글 리스트 조회", description = "게시글의 댓글 리스트 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 조회 성공", content = @Content(schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @GetMapping("/{postId}")
    public ResponseEntity<SuccessResponseDto<List<CommentDto>>> commentList(@PathVariable("postId") Long postId){
        List<CommentDto> commentList = commentQueryService.getCommentList(postId);
        SuccessResponseDto<List<CommentDto>> successResponse =
                controllerUtil.createSuccessResponse(commentList, HttpServletResponse.SC_OK);
        return ResponseEntity.ok(successResponse);
    }


    @Operation(summary = "댓글 삭제", description = "게시글의 댓글 삭제 , 인증 헤더 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "댓글 삭제 성공", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러 또는 권한 부족", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(Authentication authentication,
                                              @PathVariable("commentId") Long commentId){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        commentService.commentDelete(loginId,commentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "댓글 수정", description = "게시글의 댓글 수정 , 인증 헤더 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공", content = @Content(schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "값 검증 에러", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러 또는 권한 부족", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @PatchMapping("/{commentId}")
    public ResponseEntity<SuccessResponseDto<CommentDto>> editComment(Authentication authentication,
                                                                      @PathVariable("commentId") Long commentId,
                                                                      @RequestBody @Validated CommentRequestDto commentRequestDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long editCommentId = commentService.commentEdit(loginId, commentId, commentRequestDto);
        CommentDto commentDto = commentQueryService.getCommentDto(editCommentId);
        SuccessResponseDto<CommentDto> successResponse =
                controllerUtil.createSuccessResponse(commentDto, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }
}
