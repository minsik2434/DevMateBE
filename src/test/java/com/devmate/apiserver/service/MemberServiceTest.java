package com.devmate.apiserver.service;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.dto.member.request.MemberRegisterDto;
import com.devmate.apiserver.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    MemberRegisterDto mockDto = new MemberRegisterDto();
    @BeforeEach
    void initMember(){
        mockDto.setLoginId("test");
        mockDto.setPassword("testPassword");
        mockDto.setConfirmPassword("testPassword");
        mockDto.setName("testMember");
        mockDto.setNickName("testNick");
        mockDto.setExperienced(true);
        mockDto.setInterests(new ArrayList());
    }

    @Test
    void memberSave_passwordEncryptedTest(){
        memberService.registerMember(mockDto);
        Optional<Member> optionalMember = memberRepository.findByLoginId(mockDto.getLoginId());
        Member findMember;
        if(optionalMember.isPresent()){
            findMember = optionalMember.get();
        }
        else{
            throw new NoSuchElementException();
        }
        assertThat(findMember.getLoginId()).isEqualTo(mockDto.getLoginId());
        assertThat(passwordEncoder.matches(mockDto.getPassword(), findMember.getPassword())).isTrue();
        assertThat(findMember.getName()).isEqualTo(mockDto.getName());
        assertThat(findMember.getNickName()).isEqualTo(mockDto.getNickName());
        assertThat(findMember.isExperienced()).isEqualTo(mockDto.getExperienced());
    }

}