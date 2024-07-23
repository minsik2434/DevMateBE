package com.devmate.apiserver.service;

import com.devmate.apiserver.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    CustomUserDetailService customUserDetailService;


    @Test
    @DisplayName("회원 아이디 조회")
    void loadUserByUsernameTest(){
        given(memberRepository.findByLoginId("test")).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> customUserDetailService.loadUserByUsername("test"));
        assertThat(throwable).isInstanceOf(UsernameNotFoundException.class).hasMessageContaining("해당 아이디가 존재하지 않습니다.");
    }
}