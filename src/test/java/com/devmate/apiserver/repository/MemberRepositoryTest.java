package com.devmate.apiserver.repository;

import com.devmate.apiserver.domain.Interest;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.MemberInterest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    InterestRepository interestRepository;
    @Autowired
    MemberInterestRepository memberInterestRepository;
    Member member;
    Interest interest;
    MemberInterest memberInterest;

    @BeforeEach
    void init(){
        member = new Member(
                "test",
                "test",
                "테스터",
                "닉네임",
                true,
                "testURL");

        interest = new Interest("Interest");
        memberInterest = new MemberInterest(member,interest);
    }

    @Test
    @DisplayName("로그인 아이디로 회원 조회")
    void findByLoginIdTest(){
        //Given
        memberRepository.save(member);
        interestRepository.save(interest);
        memberInterestRepository.save(memberInterest);

        //When
        Optional<Member> findMember = memberRepository.findByLoginId(member.getLoginId());

        //Then
        assertThat(findMember.isPresent()).isTrue();
        assertThat(findMember.get().getId()).isEqualTo(member.getId());
        assertThat(findMember.get().getLoginId()).isEqualTo(member.getLoginId());
    }

    @Test
    @DisplayName("닉네임으로 회원 조회")
    void findByNickNameTest(){
        //Given
        memberRepository.save(member);
        interestRepository.save(interest);
        memberInterestRepository.save(memberInterest);

        //When
        Optional<Member> findMember = memberRepository.findByNickName(member.getNickName());

        //Then
        assertThat(findMember.isPresent()).isTrue();
        assertThat(findMember.get().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("로그인 아이디로 회원조회(fetch Interests)")
    void findByLoginIdTest_fetchInterest(){
        //Given
        memberRepository.save(member);
        interestRepository.save(interest);
        memberInterestRepository.save(memberInterest);

        //When
        Optional<Member> findMember =
                memberRepository.findMemberAndInterestsByLoginId(member.getLoginId());

        //Then
        assertThat(findMember.isPresent()).isTrue();
        assertThat(findMember.get().getMemberInterests()).isNotNull();
        assertThat(findMember.get().getMemberInterests().size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("닉네임으로 회원조회(fetch Interests)")
    void findByNickNameTest_fetchInterest(){
        //Given
        memberRepository.save(member);
        interestRepository.save(interest);
        memberInterestRepository.save(memberInterest);

        //When
        Optional<Member> findMember =
                memberRepository.findMemberAndInterestsByNickName(member.getNickName());

        //Then
        assertThat(findMember.isPresent()).isTrue();
        assertThat(findMember.get().getMemberInterests()).isNotNull();
        assertThat(findMember.get().getMemberInterests().size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("아이디로 회원조회(fetch Interests)")
    void  findByIdTest_fetchInterests(){
        //Given
        memberRepository.save(member);
        interestRepository.save(interest);
        memberInterestRepository.save(memberInterest);

        //When
        Optional<Member> findMember =
                memberRepository.findMemberAndInterestsById(member.getId());

        //Then
        assertThat(findMember.isPresent()).isTrue();
        assertThat(findMember.get().getMemberInterests()).isNotNull();
        assertThat(findMember.get().getMemberInterests().size()).isGreaterThan(0);
    }
}