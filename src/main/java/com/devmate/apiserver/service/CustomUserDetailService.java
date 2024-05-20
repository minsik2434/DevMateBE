package com.devmate.apiserver.service;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> loginMember = memberRepository.findByLoginId(loginId);
        if(loginMember.isEmpty()){
            throw new UsernameNotFoundException("해당 아이디가 존재하지 않습니다.");
        }

        return User.builder()
                .username(loginMember.get().getLoginId())
                .password(loginMember.get().getPassword())
                .roles("USER")
                .build();
    }

}
