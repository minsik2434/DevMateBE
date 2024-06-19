package com.devmate.apiserver.controller;

import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.domain.Interest;
import com.devmate.apiserver.dto.FailResponseDto;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.interest.response.InterestDto;
import com.devmate.apiserver.service.InterestService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "관심 직종 컨트롤러", description = "관심직종 API")
@RestController
@RequestMapping("/interests")
@RequiredArgsConstructor
@Slf4j
public class InterestController {
    private final ControllerUtil controllerUtil;
    private final InterestService interestService;

    @Operation(summary = "모든 관심 직종 조회", description = "관심 직종 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = InterestDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @GetMapping
    public ResponseEntity<SuccessResponseDto<List<InterestDto>>> interestList(){
        log.info("find interests");
        List<InterestDto> interestDtoList =
                interestService.findInterests().stream().map(InterestDto::new).toList();

        SuccessResponseDto<List<InterestDto>> successResponse =
                controllerUtil.createSuccessResponse(interestDtoList, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }

    @Operation(summary = "관심 직종 정보 조회", description = "관심 직종 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = InterestDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseDto<InterestDto>> interest(@PathVariable("id") Long id){
        Interest interest = interestService.findInterest(id);
        InterestDto interestDto = new InterestDto(interest);
        SuccessResponseDto<InterestDto> successResponse =
                controllerUtil.createSuccessResponse(interestDto, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }

}
