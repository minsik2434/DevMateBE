package com.devmate.apiserver.service;

import com.devmate.apiserver.common.exception.DuplicateResourceException;
import com.devmate.apiserver.common.exception.LackOfPermissionException;
import com.devmate.apiserver.domain.Good;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.dto.good.GoodDto;
import com.devmate.apiserver.repository.GoodRepository;
import com.devmate.apiserver.repository.MemberRepository;
import com.devmate.apiserver.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoodService {
    private final GoodRepository goodRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public boolean findGood(String loginId , Long postId){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new NoSuchElementException("Not Found Member"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Not Found Post"));
        Optional<Good> good = goodRepository.findGoodByMemberAndPost(member, post);
        return good.isPresent();
    }

    @Transactional
    public Long goodSave(String loginId, Long postId){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new NoSuchElementException("Not Found Member"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Not Found Post"));
        Optional<Good> good = goodRepository.findGoodByMemberAndPost(member, post);
        if(good.isPresent()){
            throw new DuplicateResourceException("is Exists Good");
        }
        Good newGood = new Good(member, post);
        Good savedGood = goodRepository.save(newGood);
        post.addGoodCount();
        return savedGood.getId();
    }

    @Transactional
    public void goodDelete(String loginId, Long goodId){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new NoSuchElementException("Not Found Member"));
        Good good = goodRepository.findGoodAndMemberAndPostById(goodId).orElseThrow(() -> new NoSuchElementException("Not Found Good"));
        if(!(member.getId().equals(good.getMember().getId()))){
            throw new LackOfPermissionException("Lack of Permission");
        }
        good.getPost().disGoodCount();
        goodRepository.delete(good);
    }
}
