package com.devmate.apiserver.service.member;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.dto.member.response.MemberDto;
import com.devmate.apiserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberRepository memberRepository;

    public MemberDto getMemberInfoById(Long memberId){
        Member member;
        if(memberRepository.findMemberAndInterestsById(memberId).isEmpty()){
            member = memberRepository.findById(memberId).orElseThrow(() ->
                    new NoSuchElementException("Member Not Found"));
        }
        else{
            member = memberRepository.findMemberAndInterestsById(memberId).get();
        }
        return new MemberDto(member);
    }

    public MemberDto getMemberInfoByLoginId(String loginId){
        Member member;
        if(memberRepository.findMemberAndInterestsByLoginId(loginId).isEmpty()){
            member = memberRepository.findByLoginId(loginId).orElseThrow(() ->
                    new NoSuchElementException("Member Not Found"));
        }
        else{
            member = memberRepository.findMemberAndInterestsByLoginId(loginId).get();
        }
        return new MemberDto(member);
    }
}
