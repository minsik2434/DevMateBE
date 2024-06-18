package com.devmate.apiserver.controller;

import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.good.GoodDto;
import com.devmate.apiserver.service.GoodService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.devmate.apiserver.domain.QGood.good;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodController {
    private final ControllerUtil controllerUtil;
    private final GoodService goodService;
    @PostMapping("/{postId}")
    public ResponseEntity<SuccessResponseDto<GoodDto>> registerGood(@PathVariable("postId") Long postId, Authentication authentication){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long savedGoodId = goodService.goodSave(loginId, postId);
        GoodDto goodDto = new GoodDto(savedGoodId);
        SuccessResponseDto<GoodDto> successResponse = controllerUtil.createSuccessResponse(goodDto, HttpServletResponse.SC_CREATED);
        return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(successResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<SuccessResponseDto<GoodDto>> findGood(@PathVariable("postId") Long postId, Authentication authentication){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        GoodDto goodDto = goodService.findGood(loginId, postId);
        SuccessResponseDto<GoodDto> successResponse =
                controllerUtil.createSuccessResponse(goodDto, HttpServletResponse.SC_OK);
        return ResponseEntity.ok(successResponse);
    }

    @DeleteMapping("/{goodId}")
    public ResponseEntity<Void> deleteGood(@PathVariable("goodId") Long goodId, Authentication authentication){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        goodService.goodDelete(loginId,goodId);
        return ResponseEntity.noContent().build();
    }

}
