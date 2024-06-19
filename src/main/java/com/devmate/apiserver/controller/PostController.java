package com.devmate.apiserver.controller;

import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.dto.FailResponseDto;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.member.response.MemberDto;
import com.devmate.apiserver.dto.post.request.MentoringRequestDto;
import com.devmate.apiserver.dto.post.request.PostRequestDto;
import com.devmate.apiserver.dto.post.request.StudyRequestDto;
import com.devmate.apiserver.dto.post.response.PostDto;
import com.devmate.apiserver.service.member.MemberService;
import com.devmate.apiserver.service.post.PostQueryService;
import com.devmate.apiserver.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Tag(name = "게시글 컨트롤러", description = "게시글 API")
@Slf4j
public class PostController {
    private final ControllerUtil controllerUtil;
    private final PostService postService;
    private final PostQueryService postQueryService;
    private final MemberService memberService;

    @Operation(summary = "게시글 등록(Q&A, Community, JobOpening, Review)", description = "category 별로 게시글 등록, 인증 헤더 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 등록 성공", content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "400", description = "값 검증 실패", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러" ,content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
    @PostMapping("/{category}")
    public ResponseEntity<SuccessResponseDto<PostDto>> postRegister(@PathVariable("category") String category,
                                                           Authentication authentication,
                                                           @RequestBody @Validated PostRequestDto postRegisterDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long postId = postService.postSave(loginId,category, postRegisterDto);
        PostDto postDto = postQueryService.postInfo(postId);
        SuccessResponseDto<PostDto> successResponse =
                controllerUtil.createSuccessResponse(postDto, HttpServletResponse.SC_OK);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @Operation(summary = "스터디 등록", description = "스터디 등록, 인증 헤더 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 등록 성공", content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "400", description = "값 검증 실패", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러" ,content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
    @PostMapping("/study")
    public ResponseEntity<SuccessResponseDto<PostDto>> studyRegister(Authentication authentication,
                                                @RequestBody @Validated StudyRequestDto studyRegisterDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long postId = postService.postSave(loginId, "study", studyRegisterDto);
        PostDto postDto = postQueryService.postInfo(postId);
        SuccessResponseDto<PostDto> successResponse =
                controllerUtil.createSuccessResponse(postDto, HttpServletResponse.SC_OK);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @Operation(summary = "멘토링 등록", description = "멘토링 등록, 인증 헤더 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 등록 성공", content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "400", description = "값 검증 실패", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러" ,content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
    @PostMapping("/mentoring")
    public ResponseEntity<SuccessResponseDto<PostDto>> mentoringRegister(Authentication authentication,
                                                                              @RequestBody @Validated MentoringRequestDto mentoringRegisterDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long postId = postService.postSave(loginId, "mentoring", mentoringRegisterDto);
        PostDto postDto = postQueryService.postInfo(postId);
        SuccessResponseDto<PostDto> successResponse =
                controllerUtil.createSuccessResponse(postDto, HttpServletResponse.SC_OK);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @Operation(summary = "게시글 조회", description = "게시글 조회 스터디나 멘토링은 추가 반환값이 있음")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공", content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseDto<PostDto>> postInfoById(@PathVariable("id") Long postId){
        PostDto postDto = postQueryService.postInfo(postId);
        SuccessResponseDto<PostDto> successResponse =
                controllerUtil.createSuccessResponse(postDto, HttpServletResponse.SC_OK);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }


    @Operation(summary = "게시글 리스트 조회", description = "게시글 리스트 조회, 쿼리 파라미터 sort(정렬 기준): comment, latest, good " +
            "sc(검색어), tag(태그 검색), page(페이지) : 0부터 시작 반환값으로 페이지 정보도 같이 반환됨")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공", content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
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


    @Operation(summary = "회원 관련 게시글 리스트 조회", description = "회원이 작성했거나 좋야요 했거나 댓글을 작성한 게시글 조회, 인증 헤더 필요, " +
            "쿼리 파라미터 type(조회 기준) : comment, post, good  쿼리 파라미터 page(페이지) : 0부터 시작 ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공", content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
    @GetMapping("/member")
    public ResponseEntity<SuccessResponseDto<Page<PostDto>>> postListByMember(Authentication authentication,
                                                                              @RequestParam(value = "type") String type,
                                                                              @RequestParam(value = "page", defaultValue = "0") int page){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long memberId = memberService.getMemberIdByLoginId(loginId);
        Page<PostDto> postDtos = postQueryService.postsByMemberFilterParam(memberId, type, page);
        SuccessResponseDto<Page<PostDto>> successResponse =
                controllerUtil.createSuccessResponse(postDtos, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }


    @Operation(summary = "게시글 조회수 추가", description = "게시글의 조회수를 증가시킴")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 조회수 증가 성공", content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
    @PostMapping("/{postId}/addview")
    public ResponseEntity<Void> addViewCount(@PathVariable("postId") Long postId){
        postService.addViewCount(postId);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "게시글 삭제", description = "게시글 삭제 , 인증 헤더 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "게시글 삭제 성공", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러 또는 권한 부족", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(Authentication authentication, @PathVariable("postId") Long postId){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        postService.deletePost(loginId, postId);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "게시글 category 별 수정(Q&A,Community, JobOpening, Review)", description = "게시글 카테고리별 수정  , 인증 헤더 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공", content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러 또는 권한 부족", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @PatchMapping("/{postId}/{category}")
    public ResponseEntity<SuccessResponseDto<PostDto>> postEdit(Authentication authentication,
                                                                @PathVariable("postId") Long postId,
                                                                @PathVariable("category") String category,
                                                                @RequestBody PostRequestDto postEditDto){
        if(!(category.equals("qna") || category.equals("review")|| category.equals("job") || category.equals("community"))){
            throw new NoSuchElementException("Not Found Category");
        }
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long editPostId = postService.editPost(loginId, postId, postEditDto);
        PostDto postDto = postQueryService.postInfo(editPostId);
        SuccessResponseDto<PostDto> successResponse =
                controllerUtil.createSuccessResponse(postDto, HttpServletResponse.SC_OK);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "스터디 수정", description = "스터디 수정  , 인증 헤더 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "스터디 수정 성공", content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러 또는 권한 부족", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @PatchMapping("/{postId}/study")
    public ResponseEntity<SuccessResponseDto<PostDto>> studyEdit(Authentication authentication,
                                                                 @PathVariable("postId") Long postId,
                                                                 @RequestBody StudyRequestDto studyRequestDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long editPostId = postService.editPost(loginId, postId, studyRequestDto);
        PostDto postDto = postQueryService.postInfo(editPostId);
        SuccessResponseDto<PostDto> successResponse =
                controllerUtil.createSuccessResponse(postDto, HttpServletResponse.SC_OK);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "멘토링 수정", description = "멘토링 수정  , 인증 헤더 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멘토링 수정 성공", content = @Content(schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러 또는 권한 부족", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @PatchMapping("/{postId}/mentoring")
    public ResponseEntity<SuccessResponseDto<PostDto>> mentoringEdit(Authentication authentication,
                                                                     @PathVariable("postId") Long postId,
                                                                     @RequestBody MentoringRequestDto mentoringRequestDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long editPostId = postService.editPost(loginId, postId, mentoringRequestDto);
        PostDto postDto = postQueryService.postInfo(editPostId);
        SuccessResponseDto<PostDto> successResponse =
                controllerUtil.createSuccessResponse(postDto, HttpServletResponse.SC_OK);
        return ResponseEntity.ok(successResponse);
    }
}
