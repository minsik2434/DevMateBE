package com.devmate.apiserver.service;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.dto.member.request.MemberRegisterDto;
import com.devmate.apiserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    public boolean isDuplicateLoginId(String loginId){
        return memberRepository.findByLoginId(loginId).isPresent();
    }

    @Transactional
    public String registerMember(MemberRegisterDto memberRegisterDto){
        Member member = new Member(memberRegisterDto, "test");
        memberRepository.save(member);
        return member.getLoginId();
    }
}
