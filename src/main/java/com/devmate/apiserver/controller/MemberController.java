package com.devmate.apiserver.controller;

import com.devmate.apiserver.common.exception.ConfirmPasswordNotMatchException;
import com.devmate.apiserver.common.exception.DuplicateResourceException;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.member.request.MemberRegisterDto;
import com.devmate.apiserver.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/register")
    public ResponseEntity<SuccessResponseDto<String>> register(@RequestBody @Validated MemberRegisterDto memberRegisterDto){
        if(!memberRegisterDto.getPassword().equals(memberRegisterDto.getConfirmPassword())){
            throw new ConfirmPasswordNotMatchException();
        }
        if(memberService.isDuplicateLoginId(memberRegisterDto.getLoginId())){
            throw new DuplicateResourceException("is exist LoginId");
        }

        String saveMemberLoginId = memberService.registerMember(memberRegisterDto);
        SuccessResponseDto<String> successResponseDto = new SuccessResponseDto<>();
        successResponseDto.setResponseTime(LocalDateTime.now());
        successResponseDto.setStatus(HttpServletResponse.SC_CREATED);
        successResponseDto.setMessage(saveMemberLoginId + " is Created");
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponseDto);
    }
}
