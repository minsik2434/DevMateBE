package com.devmate.apiserver.controller;

import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.dto.FailResponseDto;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.good.GoodDto;
import com.devmate.apiserver.service.GoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "좋아요 컨트롤러", description = "좋아요 API")
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodController {
    private final ControllerUtil controllerUtil;
    private final GoodService goodService;

    @Operation(summary = "좋아요 추가", description = "path 경로에 postId 입력, 반환값으로 좋아요 ID 반환, 인증 헤더 필요함")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "좋아요 등록 성공", content = @Content(schema = @Schema(implementation = GoodDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 오류", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 리소스", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @PostMapping("/{postId}")
    public ResponseEntity<SuccessResponseDto<GoodDto>> registerGood(@PathVariable("postId") Long postId, Authentication authentication){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long savedGoodId = goodService.goodSave(loginId, postId);
        GoodDto goodDto = new GoodDto(savedGoodId);
        SuccessResponseDto<GoodDto> successResponse = controllerUtil.createSuccessResponse(goodDto, HttpServletResponse.SC_CREATED);
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(successResponse);
    }

    @Operation(summary = "좋아요 조회", description = "게시글에 회원의 좋아요 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 조회 성공", content = @Content(schema = @Schema(implementation = GoodDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러", content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
    @GetMapping("/{postId}")
    public ResponseEntity<SuccessResponseDto<Boolean>> findGood(@PathVariable("postId") Long postId, Authentication authentication){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        boolean isGood = goodService.findGood(loginId, postId);
        SuccessResponseDto<Boolean> successResponse =
                controllerUtil.createSuccessResponse(isGood, HttpServletResponse.SC_OK);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "좋아요 삭제", description = "좋아요 ID로 좋아요 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "좋아요 삭제 성공", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러 또는 권한 부족", content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
    @DeleteMapping("/{goodId}")
    public ResponseEntity<Void> deleteGood(@PathVariable("goodId") Long goodId, Authentication authentication){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        goodService.goodDelete(loginId,goodId);
        return ResponseEntity.noContent().build();
    }

}
