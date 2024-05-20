package com.devmate.apiserver.controller;

import com.devmate.apiserver.common.exception.ConfirmPasswordNotMatchException;
import com.devmate.apiserver.common.exception.DuplicateResourceException;
import com.devmate.apiserver.common.jwt.JwtToken;
import com.devmate.apiserver.dto.FailResponseDto;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.member.request.EditProfileDto;
import com.devmate.apiserver.dto.member.request.MemberRegisterDto;
import com.devmate.apiserver.dto.member.request.SignInDto;
import com.devmate.apiserver.dto.member.response.MemberDto;
import com.devmate.apiserver.service.MemberQueryService;
import com.devmate.apiserver.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberQueryService memberQueryService;

    @GetMapping("test")
    public String test(){
        return "test";
    }

    
    @PostMapping("/register")
    public ResponseEntity<SuccessResponseDto<MemberDto>> register(@RequestBody @Validated MemberRegisterDto memberRegisterDto){
        if(!memberRegisterDto.getPassword().equals(memberRegisterDto.getConfirmPassword())){
            throw new ConfirmPasswordNotMatchException();
        }
        if(memberService.isDuplicateLoginId(memberRegisterDto.getLoginId())){
            throw new DuplicateResourceException("is exists LoginId");
        }
        if(memberService.isDuplicateNickName(memberRegisterDto.getNickName())){
            throw new DuplicateResourceException("is exists NickName");
        }

        Long saveMemberId = memberService.registerMember(memberRegisterDto);
        MemberDto memberDto = memberQueryService.gatMemberInfo(saveMemberId);

        SuccessResponseDto<MemberDto> successResponse =
                createSuccessResponse(memberDto, HttpServletResponse.SC_CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<SuccessResponseDto<JwtToken>> signIn(@RequestBody @Validated SignInDto signInDto){
        JwtToken jwtToken = memberService.signIn(signInDto.getLoginId(),signInDto.getPassword());

        SuccessResponseDto<JwtToken> successResponse =
                createSuccessResponse(jwtToken, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> withDraw(Authentication authentication){
        String loginId = getAuthorizedLoginId(authentication);
        memberService.deleteMember(loginId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<SuccessResponseDto<MemberDto>> editProfile(Authentication authentication,
                                              @RequestBody @Validated EditProfileDto editProfileDto){
        if(memberService.isDuplicateNickName(editProfileDto.getNickName())){
            throw new DuplicateResourceException("is exists NickName");
        }
        String loginId = getAuthorizedLoginId(authentication);
        Long memberId = memberService.editMember(loginId, editProfileDto);
        MemberDto memberDto = memberQueryService.gatMemberInfo(memberId);
        SuccessResponseDto<MemberDto> successResponse
                = createSuccessResponse(memberDto, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }


    private String getAuthorizedLoginId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }


    private <T> SuccessResponseDto<T> createSuccessResponse(T data, int status){
        SuccessResponseDto<T> successResponseDto = new SuccessResponseDto<>();
        successResponseDto.setResponseTime(LocalDateTime.now());
        successResponseDto.setStatus(status);
        successResponseDto.setData(data);
        return successResponseDto;
    }

}
