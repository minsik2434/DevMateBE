package com.devmate.apiserver.repository;

import com.devmate.apiserver.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    Member member;

    @BeforeEach
    void init(){
        member = new Member(
                "test",
                "test",
                "테스터",
                "닉네임",
                true,
                "testURL");
    }

    @Test
    @DisplayName("로그인 아이디로 회원 조회")
    void findByLoginIdTest(){
        memberRepository.save(member);

        Optional<Member> findMember = memberRepository.findByLoginId(member.getLoginId());
        assertThat(findMember.isPresent()).isTrue();
        assertThat(findMember.get().getId()).isEqualTo(member.getId());
        assertThat(findMember.get().getLoginId()).isEqualTo(member.getLoginId());
    }
}