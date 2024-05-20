package com.devmate.apiserver.service;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.dto.member.request.EditProfileDto;
import com.devmate.apiserver.dto.member.request.MemberRegisterDto;
import com.devmate.apiserver.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    MemberRegisterDto mockRegisterDto = new MemberRegisterDto();
    EditProfileDto mockEditProfileDto = new EditProfileDto();
    @BeforeEach
    void initRegisterDto(){
        mockRegisterDto.setLoginId("test");
        mockRegisterDto.setPassword("testPassword");
        mockRegisterDto.setConfirmPassword("testPassword");
        mockRegisterDto.setName("testMember");
        mockRegisterDto.setNickName("testNick");
        mockRegisterDto.setExperienced(true);
        mockRegisterDto.setInterests(new ArrayList());
    }

    @BeforeEach
    void initEditProfileDto(){
        mockEditProfileDto.setName("EditTestName");
        mockEditProfileDto.setNickName("EditTestNick");
        mockEditProfileDto.setExperienced(false);
        mockEditProfileDto.setImgUrl("EditTestImg");
    }

    @Test
    void memberSave_passwordEncryptedTest(){
        memberService.registerMember(mockRegisterDto);
        Optional<Member> optionalMember = memberRepository.findByLoginId(mockRegisterDto.getLoginId());
        Member findMember;
        if(optionalMember.isPresent()){
            findMember = optionalMember.get();
        }
        else{
            throw new NoSuchElementException();
        }
        assertThat(findMember.getLoginId()).isEqualTo(mockRegisterDto.getLoginId());
        assertThat(passwordEncoder.matches(mockRegisterDto.getPassword(), findMember.getPassword())).isTrue();
        assertThat(findMember.getName()).isEqualTo(mockRegisterDto.getName());
        assertThat(findMember.getNickName()).isEqualTo(mockRegisterDto.getNickName());
        assertThat(findMember.isExperienced()).isEqualTo(mockRegisterDto.getExperienced());
    }

    @Test
    void memberEditTest(){
        memberService.registerMember(mockRegisterDto);
        Long editMemberId = memberService.editMember(mockRegisterDto.getLoginId(), mockEditProfileDto);
        Optional<Member> findMember = memberRepository.findById(editMemberId);
        if(findMember.isEmpty()){
            throw new NoSuchElementException();
        }
        Member member = findMember.get();
        assertThat(member.getName()).isEqualTo(mockEditProfileDto.getName());
        assertThat(member.getNickName()).isEqualTo(mockEditProfileDto.getNickName());
        assertThat(member.isExperienced()).isEqualTo(mockEditProfileDto.getExperienced());
        assertThat(member.getProfileImgUrl()).isEqualTo(mockEditProfileDto.getImgUrl());
    }
}