package com.devmate.apiserver.service;

import com.devmate.apiserver.domain.*;
import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import com.devmate.apiserver.repository.post.HashTagRepository;
import com.devmate.apiserver.repository.MemberRepository;
import com.devmate.apiserver.repository.post.PostHashTagRepository;
import com.devmate.apiserver.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final HashTagRepository hashTagRepository;
    private final PostHashTagRepository postHashTagRepository;
    public Long postSave(String loginId, String category, PostRegisterDto postRegisterDto){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new NoSuchElementException("member not exist"));
        Post postByCategory = getPostByCategory(member, category, postRegisterDto);
        Post savedPost = postRepository.save(postByCategory);

        if(!postRegisterDto.getTags().isEmpty()){
            List<String> tags = postRegisterDto.getTags();
            for (String tag : tags) {
                Optional<HashTag> findHashtag = hashTagRepository.findByName(tag);
                if(findHashtag.isPresent()){
                    PostHashTag postHashTag = new PostHashTag(savedPost, findHashtag.get());
                    postHashTagRepository.save(postHashTag);
                }
                else{
                    HashTag hashTag = new HashTag(tag);
                    HashTag savedHashTag = hashTagRepository.save(hashTag);
                    PostHashTag postHashTag = new PostHashTag(savedPost, savedHashTag);
                    postHashTagRepository.save(postHashTag);
                }
            }
        }

        return savedPost.getId();
    }

    private Post getPostByCategory(Member member, String category, PostRegisterDto postRegisterDto){
        Post post;
        if(category.equals("qna")){
            post = new Qna(member,postRegisterDto);
        }
        else if(category.equals("community")){
            post = new Community(member ,postRegisterDto);
        }
        else if(category.equals("job")){
            post = new JobOpening(member,postRegisterDto);
        }
        else if(category.equals("review")){
            post = new Review(member,postRegisterDto);
        }
        else{
            throw new NoSuchElementException("Not Found Category");
        }
        return post;
    }
}
