package com.devmate.apiserver.controller;

import com.devmate.apiserver.common.exception.ConfirmPasswordNotMatchException;
import com.devmate.apiserver.common.exception.DuplicateResourceException;
import com.devmate.apiserver.common.jwt.JwtToken;
import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.member.request.EditProfileDto;
import com.devmate.apiserver.dto.member.request.MemberRegisterDto;
import com.devmate.apiserver.dto.member.request.SignInDto;
import com.devmate.apiserver.dto.member.response.MemberDto;
import com.devmate.apiserver.service.MemberQueryService;
import com.devmate.apiserver.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberQueryService memberQueryService;
    private final ControllerUtil controllerUtil;

    @GetMapping("test")
    public String test(){
        return "test";
    }

    
    @PostMapping("/register")
    public ResponseEntity<SuccessResponseDto<MemberDto>> register(@RequestBody @Validated MemberRegisterDto memberRegisterDto){
        if(!memberRegisterDto.getPassword().equals(memberRegisterDto.getConfirmPassword())){
            throw new ConfirmPasswordNotMatchException("Confirm Password Not Matched");
        }
        if(memberService.isDuplicateLoginId(memberRegisterDto.getLoginId())){
            throw new DuplicateResourceException("is exists LoginId");
        }
        if(memberService.isDuplicateNickName(memberRegisterDto.getNickName())){
            throw new DuplicateResourceException("is exists NickName");
        }

        Long saveMemberId = memberService.registerMember(memberRegisterDto);
        MemberDto memberDto = memberQueryService.getMemberInfo(saveMemberId);

        SuccessResponseDto<MemberDto> successResponse =
                controllerUtil.createSuccessResponse(memberDto, HttpServletResponse.SC_CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<SuccessResponseDto<JwtToken>> signIn(@RequestBody @Validated SignInDto signInDto){
        JwtToken jwtToken = memberService.signIn(signInDto.getLoginId(),signInDto.getPassword());

        SuccessResponseDto<JwtToken> successResponse =
                controllerUtil.createSuccessResponse(jwtToken, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> withDraw(Authentication authentication){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        memberService.deleteMember(loginId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<SuccessResponseDto<MemberDto>> editProfile(Authentication authentication,
                                              @RequestBody @Validated EditProfileDto editProfileDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        List<Long> interests = editProfileDto.getInterests();
        Long memberId = memberService.editMember(loginId, editProfileDto);
        MemberDto memberDto = memberQueryService.getMemberInfo(memberId);
        SuccessResponseDto<MemberDto> successResponse
                = controllerUtil.createSuccessResponse(memberDto, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }


}
