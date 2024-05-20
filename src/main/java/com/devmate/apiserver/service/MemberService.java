package com.devmate.apiserver.service;

import com.devmate.apiserver.common.jwt.JwtToken;
import com.devmate.apiserver.common.jwt.JwtTokenProvider;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.dto.member.request.EditProfileDto;
import com.devmate.apiserver.dto.member.request.MemberRegisterDto;
import com.devmate.apiserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider provider;

    public boolean isDuplicateLoginId(String loginId){
        return memberRepository.findByLoginId(loginId).isPresent();
    }

    public boolean isDuplicateNickName(String nickName){
        return memberRepository.findByNickName(nickName).isPresent();
    }

    @Transactional
    public Long registerMember(MemberRegisterDto memberRegisterDto){
        Member member = new Member(memberRegisterDto,
                passwordEncoder.encode(memberRegisterDto.getPassword()));
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public JwtToken signIn(String loginId, String password){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginId, password);

        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);
        return provider.generateToken(authentication);
    }

    @Transactional
    public void deleteMember(String loginId){
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        if(optionalMember.isEmpty()){
            throw new NoSuchElementException();
        }
        memberRepository.delete(optionalMember.get());
    }

    @Transactional
    public Long editMember(String loginId, EditProfileDto editProfileDto){
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        if(optionalMember.isEmpty()){
            throw new NoSuchElementException("member not exist");
        }
        Member member = optionalMember.get();
        member.editMember(editProfileDto);
        return member.getId();
    }
}
