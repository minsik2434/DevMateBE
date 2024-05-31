package com.devmate.apiserver.service.post;

import com.devmate.apiserver.domain.*;
import com.devmate.apiserver.dto.post.request.MentoringRegisterDto;
import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import com.devmate.apiserver.dto.post.request.RegisterDto;
import com.devmate.apiserver.dto.post.request.StudyRegisterDto;
import com.devmate.apiserver.repository.post.HashTagRepository;
import com.devmate.apiserver.repository.MemberRepository;
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
    public <T extends RegisterDto> Long postSave(String loginId, String category, T registerDto){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new NoSuchElementException("Member Not Found"));
        Post postByCategory = getPostByCategory(member, category, registerDto);

        if(!registerDto.getTags().isEmpty()){
            List<String> tags = registerDto.getTags();
            for (String tag : tags) {
                Optional<HashTag> findHashtag = hashTagRepository.findByName(tag);
                if(findHashtag.isPresent()){
                    new PostHashTag(postByCategory, findHashtag.get());
                }
                else{
                    HashTag hashTag = new HashTag(tag);
                    HashTag savedHashTag = hashTagRepository.save(hashTag);
                    new PostHashTag(postByCategory, savedHashTag);
                }
            }
        }
        Post savedPost = postRepository.save(postByCategory);
        return savedPost.getId();
    }

    private <T extends RegisterDto> Post getPostByCategory(Member member, String category, T registerDto){
        Post post;
        if(category.equals("qna")){
            post = new Qna(member,(PostRegisterDto) registerDto);
        }
        else if(category.equals("community")){
            post = new Community(member ,(PostRegisterDto) registerDto);
        }
        else if(category.equals("job")){
            post = new JobOpening(member,(PostRegisterDto) registerDto);
        }
        else if(category.equals("review")){
            post = new Review(member,(PostRegisterDto) registerDto);
        }
        else if(category.equals("study")){
            post = new Study(member, (StudyRegisterDto) registerDto);
        }
        else if(category.equals("mentoring")){
            post = new Mento(member, (MentoringRegisterDto) registerDto);
        }
        else{
            throw new NoSuchElementException("Not Found Category");
        }
        return post;
    }
}
