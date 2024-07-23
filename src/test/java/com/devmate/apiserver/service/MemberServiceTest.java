package com.devmate.apiserver.service;

import com.devmate.apiserver.common.jwt.JwtTokenProvider;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.dto.member.request.EditProfileDto;
import com.devmate.apiserver.dto.member.request.MemberRegisterDto;
import com.devmate.apiserver.repository.InterestRepository;
import com.devmate.apiserver.repository.MemberRepository;
import com.devmate.apiserver.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.given;


@Transactional
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    InterestRepository interestRepository;
    @InjectMocks
    MemberService memberService;
    @Spy
    BCryptPasswordEncoder encoder;

    @Test
    @DisplayName("로그인 아이디로 회원 조회시 예외")
    void findLoginIdExceptionTest(){
        //Given
        given(memberRepository.findByLoginId("test")).willReturn(Optional.empty());

        //When
        Throwable throwable = catchThrowable(() -> memberService.getMemberIdByLoginId("test"));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Member");
    }

    @Test
    @DisplayName("회원가입시 interest 예외")
    void registerMemberExceptionTest(){
        MemberRegisterDto memberRegisterDto = new MemberRegisterDto();
        List<Long> interestList = new ArrayList<>();
        interestList.add(1L);
        memberRegisterDto.setLoginId("test");
        memberRegisterDto.setName("테스터");
        memberRegisterDto.setNickName("닉네임");
        memberRegisterDto.setInterests(interestList);
        memberRegisterDto.setExperienced(true);
        memberRegisterDto.setPassword(encoder.encode("test123"));
        //Given
        given(interestRepository.findById(1L)).willReturn(Optional.empty());

        //When
        Throwable throwable = catchThrowable(() -> memberService.registerMember(memberRegisterDto));

        //Then
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("interest not exists");
    }

    @Test
    @DisplayName("회원 탈퇴시 예외")
    void deleteMember_Failed(){
        given(memberRepository.findByLoginId("test")).willReturn(Optional.empty());

        //When
        Throwable throwable = catchThrowable(() -> memberService.deleteMember("test"));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Member");
    }

    @Test
    @DisplayName("회원 수정시 예외_회원 존재 x")
    void editMember_Failed_NotFoundMember(){
        EditProfileDto editProfileDto = new EditProfileDto();
        List<Long> interests = new ArrayList<>();
        interests.add(1L);
        editProfileDto.setName("최민식");
        editProfileDto.setNickName("alstlr");
        editProfileDto.setExperienced(true);
        editProfileDto.setImgUrl("test");
        editProfileDto.setInterests(interests);

        given(memberRepository.findByLoginId("test")).willReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> memberService.editMember("test",editProfileDto));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Member");
    }

}