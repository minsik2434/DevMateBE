package com.devmate.apiserver.service.member;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.dto.member.response.MemberDto;
import com.devmate.apiserver.dto.member.response.SimpleMemberDto;
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

    public SimpleMemberDto getSimpleMemberInfoByNickName(String nickName){
        Optional<Member> memberAndInterestsByNickName = memberRepository.findMemberAndInterestsByNickName(nickName);
        Member member = memberAndInterestsByNickName.orElseGet(() -> memberRepository.findByNickName(nickName).orElseThrow(() ->
                new NoSuchElementException("Not Found Member")));
        return new SimpleMemberDto(member);
    }

    public MemberDto getMemberInfoById(Long memberId){
        Optional<Member> memberAndInterestsById = memberRepository.findMemberAndInterestsById(memberId);
        Member member = memberAndInterestsById.orElseGet(() -> memberRepository.findById(memberId).orElseThrow(() ->
                new NoSuchElementException("Not Found Member")));
        return new MemberDto(member);
    }
    public MemberDto getMemberInfoByLoginId(String loginId){
        Optional<Member> memberAndInterestsByLoginId = memberRepository.findMemberAndInterestsByLoginId(loginId);
        Member member = memberAndInterestsByLoginId.orElseGet(() -> memberRepository.findByLoginId(loginId).orElseThrow(() ->
                new NoSuchElementException("Not Found Member")));
        return new MemberDto(member);
    }
}
