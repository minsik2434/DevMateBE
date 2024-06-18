package com.devmate.apiserver.controller;

import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.post.request.MentoringRequestDto;
import com.devmate.apiserver.dto.post.request.PostRequestDto;
import com.devmate.apiserver.dto.post.request.StudyRequestDto;
import com.devmate.apiserver.dto.post.response.PostDto;
import com.devmate.apiserver.service.member.MemberService;
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

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final ControllerUtil controllerUtil;
    private final PostService postService;
    private final PostQueryService postQueryService;
    private final MemberService memberService;
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

    @PostMapping("/{postId}/addview")
    public ResponseEntity<Void> addViewCount(@PathVariable("postId") Long postId){
        postService.addViewCount(postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(Authentication authentication, @PathVariable("postId") Long postId){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        postService.deletePost(loginId, postId);

        return ResponseEntity.noContent().build();
    }

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
