package com.devmate.apiserver.service.member;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.dto.member.response.MemberDto;
import com.devmate.apiserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberQueryService {
    private final MemberRepository memberRepository;

    public MemberDto getMemberInfoById(Long memberId){
        log.info("memberInfoIdService start");
        Optional<Member> memberAndInterestsById = memberRepository.findMemberAndInterestsById(memberId);
        Member member = memberAndInterestsById.orElseGet(() -> memberRepository.findById(memberId).orElseThrow(() ->
                new NoSuchElementException("Member Not Found")));
        log.info("memberInfoIdService end");
        return new MemberDto(member);
    }
    public MemberDto getMemberInfoByLoginId(String loginId){
        log.info("memberInfoLoginIdService start");
        Optional<Member> memberAndInterestsByLoginId = memberRepository.findMemberAndInterestsByLoginId(loginId);
        Member member = memberAndInterestsByLoginId.orElseGet(() -> memberRepository.findByLoginId(loginId).orElseThrow(() ->
                new NoSuchElementException("Member Not Found")));
        log.info("memberInfoLoginIdService end");
        return new MemberDto(member);
    }
}
