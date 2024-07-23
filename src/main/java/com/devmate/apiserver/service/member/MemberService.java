package com.devmate.apiserver.service.member;

import com.devmate.apiserver.common.exception.DuplicateResourceException;
import com.devmate.apiserver.common.exception.IdOrPasswordIncorrectException;
import com.devmate.apiserver.common.jwt.JwtToken;
import com.devmate.apiserver.common.jwt.JwtTokenProvider;
import com.devmate.apiserver.domain.Interest;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.MemberInterest;
import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.dto.member.request.EditProfileDto;
import com.devmate.apiserver.dto.member.request.MemberRegisterDto;
import com.devmate.apiserver.repository.CommentRepository;
import com.devmate.apiserver.repository.GoodRepository;
import com.devmate.apiserver.repository.InterestRepository;
import com.devmate.apiserver.repository.MemberRepository;
import com.devmate.apiserver.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final InterestRepository interestRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final GoodRepository goodRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider provider;

    public boolean isDuplicateLoginId(String loginId){
        return memberRepository.findByLoginId(loginId).isPresent();
    }

    public boolean isDuplicateNickName(String nickName){
        return memberRepository.findByNickName(nickName).isPresent();
    }

    public Long getMemberIdByLoginId(String loginId){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new NoSuchElementException("Not Found Member"));
        return member.getId();
    }

    @Transactional
    public Long registerMember(MemberRegisterDto memberRegisterDto){
        Member member = new Member(memberRegisterDto,
                passwordEncoder.encode(memberRegisterDto.getPassword()));
        if(!memberRegisterDto.getInterests().isEmpty()){
            for(Long interestId : memberRegisterDto.getInterests()){
                Interest interest = interestRepository.findById(interestId).orElseThrow(
                        ()-> new NoSuchElementException("interest not exists"));
                new MemberInterest(member,interest);
            }
        }
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public JwtToken signIn(String loginId, String password){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginId, password);

        Authentication authentication = null;
        try {
            authentication = authenticationManagerBuilder
                    .getObject()
                    .authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new IdOrPasswordIncorrectException("Id or Password Incorrect");
        }
        return provider.generateToken(authentication);
    }

    @Transactional
    public void deleteMember(String loginId){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() ->
                new NoSuchElementException("Not Found Member"));
        List<Post> postByMemberComment = postRepository.findPostByMemberComment(member);
        List<Post> postsByMemberGood = postRepository.findPostsByMemberGood(member);
        for (Post post : postByMemberComment) {
            int commentCount = commentRepository.countByCommentByMemberPost(post, member);
            post.changeCommentCount(commentCount);
        }
        for (Post post : postsByMemberGood) {
            post.disGoodCount();
        }
        memberRepository.delete(member);
    }

    @Transactional
    public Long editMember(String loginId, EditProfileDto editProfileDto){
        Member member;
        if(memberRepository.findMemberAndInterestsByLoginId(loginId).isEmpty()){
            member = memberRepository.findByLoginId(loginId).orElseThrow(() ->
                    new NoSuchElementException("Not Found Member"));
        }
        else{
            member = memberRepository.findMemberAndInterestsByLoginId(loginId).get();
        }
        if(!member.getNickName().equals(editProfileDto.getNickName())){
            if (memberRepository.findByNickName(editProfileDto.getNickName()).isPresent()) {
               throw new DuplicateResourceException("is exists NickName");
            }
        }
        member.getMemberInterests().clear();
        member.editMember(editProfileDto);
        if(!editProfileDto.getInterests().isEmpty()){
            for(Long interestId : editProfileDto.getInterests()){
                Interest interest = interestRepository.findById(interestId).orElseThrow(
                        ()-> new NoSuchElementException("interest not exists"));
                new MemberInterest(member,interest);
            }
        }
        return member.getId();
    }
}
