package com.devmate.apiserver.controller;

import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.domain.Interest;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.interest.response.InterestDto;
import com.devmate.apiserver.service.InterestService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/interests")
@RequiredArgsConstructor
public class InterestController {
    private final ControllerUtil controllerUtil;
    private final InterestService interestService;
    @GetMapping
    public ResponseEntity<SuccessResponseDto<List<InterestDto>>> interestList(){
        List<InterestDto> interestDtoList =
                interestService.findInterests().stream().map(InterestDto::new).toList();

        SuccessResponseDto<List<InterestDto>> successResponse =
                controllerUtil.createSuccessResponse(interestDtoList, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }

}
