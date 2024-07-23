package com.devmate.apiserver.service.member;

import com.devmate.apiserver.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberQueryServiceTest {

    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    MemberQueryService memberQueryService;

    @Test
    @DisplayName("회원 정보 조회시 예외_닉네임")
    void getMemberInfoNickNameException(){
        given(memberRepository.findMemberAndInterestsByNickName("test")).willReturn(Optional.empty());
        given(memberRepository.findByNickName("test")).willReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> memberQueryService.getSimpleMemberInfoByNickName("test"));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Member");
    }

    @Test
    @DisplayName("회원 정보 조회시 예외_아이디")
    void getMemberInfoIdException(){
        given(memberRepository.findMemberAndInterestsById(1L)).willReturn(Optional.empty());
        given(memberRepository.findById(1L)).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> memberQueryService.getMemberInfoById(1L));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Member");
    }

    @Test
    @DisplayName("회원 정보 조회시 예외_로그인 아이디")
    void getMemberInfoLoginIdException(){
        given(memberRepository.findMemberAndInterestsByLoginId("test")).willReturn(Optional.empty());
        given(memberRepository.findByLoginId("test")).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> memberQueryService.getMemberInfoByLoginId("test"));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Member");
    }
}