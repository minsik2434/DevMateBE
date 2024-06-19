package com.devmate.apiserver.controller;

import com.devmate.apiserver.common.exception.ConfirmPasswordNotMatchException;
import com.devmate.apiserver.common.exception.DuplicateResourceException;
import com.devmate.apiserver.common.jwt.JwtToken;
import com.devmate.apiserver.controller.util.ControllerUtil;
import com.devmate.apiserver.dto.FailResponseDto;
import com.devmate.apiserver.dto.SuccessResponseDto;
import com.devmate.apiserver.dto.member.request.EditProfileDto;
import com.devmate.apiserver.dto.member.request.MemberRegisterDto;
import com.devmate.apiserver.dto.member.request.SignInDto;
import com.devmate.apiserver.dto.member.response.MemberDto;
import com.devmate.apiserver.dto.member.response.SimpleMemberDto;
import com.devmate.apiserver.service.member.MemberQueryService;
import com.devmate.apiserver.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Tag(name = "회원 컨트롤러", description = "회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberQueryService memberQueryService;
    private final ControllerUtil controllerUtil;

    @Operation(summary = "회원 조회(닉네임)",
            description = "인증 헤더 없이 path 경로에 nickName 으로 조회 반환값으로 회원 닉네임, 회원 이미지 URL, 관심직종 반환함")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 조회 성공", content = @Content(schema = @Schema(implementation = SimpleMemberDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
    })
    @GetMapping("/{nickName}")
    public ResponseEntity<SuccessResponseDto<SimpleMemberDto>> MemberByNickName(@PathVariable("nickName") String nickName){
        SimpleMemberDto simpleMemberDto = memberQueryService.getSimpleMemberInfoByNickName(nickName);
        SuccessResponseDto<SimpleMemberDto> successResponse =
                controllerUtil.createSuccessResponse(simpleMemberDto, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }

    @Operation(summary = "회원조회(인증헤더)",
            description = "인증 헤더 필요 반환값으로 회원 로그인 아이디, 회원 이름, 회원 닉네임, 경력 유무, 프로필 이미지 URL, 관심 직종 반환")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 조회 성공", content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
    @GetMapping
    public ResponseEntity<SuccessResponseDto<MemberDto>> info(Authentication authentication){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        MemberDto memberDto = memberQueryService.getMemberInfoByLoginId(loginId);
        SuccessResponseDto<MemberDto> successResponse =
                controllerUtil.createSuccessResponse(memberDto, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }

    @Operation(summary = "회원 로그인 아이디, 회원 닉네임 중복 체크",
    description = "쿼리 파라미터 (loginId, nickName) 요청 필요, path 경로에 중복 체크 값 입력")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 조회 성공", content = @Content(schema = @Schema(example = "Duplicate"))),
    })
    @GetMapping("/{value}/check")
    public ResponseEntity<SuccessResponseDto<String>> duplicateValue(@PathVariable("value") String value,
                                                                       @RequestParam("type") String type){
        boolean isCheck;
        if(type.equals("loginId")){
            isCheck = memberService.isDuplicateLoginId(value);
        }
        else if(type.equals("nickName")){
            isCheck = memberService.isDuplicateNickName(value);
        }
        else{
            throw new NoSuchElementException("Not Found Type");
        }
        SuccessResponseDto<String> successResponse;
        if(isCheck){
            successResponse = controllerUtil.createSuccessResponse("Duplicate",HttpServletResponse.SC_OK);
        }
        else{
            successResponse =controllerUtil.createSuccessResponse("Not Duplicate",HttpServletResponse.SC_OK);
        }
        return ResponseEntity.ok(successResponse);
    }

    @Operation(summary = "회원 등록", description = "비밀번호와 비밀번호 확인이 일치해야함, 이미 존재하는 ID 사용 불가, 이미 존재하는 닉네임 사용 불가")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원 등록 성공", content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "400", description = "값 검증 실패", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "중복 리소스" ,content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
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
        MemberDto memberDto = memberQueryService.getMemberInfoById(saveMemberId);

        SuccessResponseDto<MemberDto> successResponse =
                controllerUtil.createSuccessResponse(memberDto, HttpServletResponse.SC_CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @Operation(summary = "로그인", description = "로그인 성공시 토큰 발급")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = JwtToken.class))),
            @ApiResponse(responseCode = "400", description = "값 검증 실패", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "로그인 실패",content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
    @PostMapping("/signin")
    public ResponseEntity<SuccessResponseDto<JwtToken>> signIn(@RequestBody @Validated SignInDto signInDto){
        log.info("signin");
        JwtToken jwtToken = memberService.signIn(signInDto.getLoginId(),signInDto.getPassword());

        SuccessResponseDto<JwtToken> successResponse =
                controllerUtil.createSuccessResponse(jwtToken, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴, 인증 토큰 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "탈퇴 성공"),
            @ApiResponse(responseCode = "401", description = "인증에러", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
    @DeleteMapping
    public ResponseEntity<Void> withDraw(Authentication authentication){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        memberService.deleteMember(loginId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회원 수정", description = "회원 수정, 인증 토큰 필요")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공", content = @Content(schema = @Schema(implementation = MemberDto.class))),
            @ApiResponse(responseCode = "400", description = "값 검증 에러", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "인증 에러", content = @Content(schema = @Schema(implementation = FailResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스",content = @Content(schema = @Schema(implementation = FailResponseDto.class)))
    })
    @PatchMapping
    public ResponseEntity<SuccessResponseDto<MemberDto>> editProfile(Authentication authentication,
                                              @RequestBody @Validated EditProfileDto editProfileDto){
        String loginId = controllerUtil.getAuthorizedLoginId(authentication);
        Long memberId = memberService.editMember(loginId, editProfileDto);
        MemberDto memberDto = memberQueryService.getMemberInfoById(memberId);
        SuccessResponseDto<MemberDto> successResponse
                = controllerUtil.createSuccessResponse(memberDto, HttpServletResponse.SC_OK);
        return ResponseEntity.ok().body(successResponse);
    }
}
