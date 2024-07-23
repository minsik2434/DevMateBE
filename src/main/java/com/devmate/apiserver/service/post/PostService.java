package com.devmate.apiserver.service.post;

import com.devmate.apiserver.common.exception.LackOfPermissionException;
import com.devmate.apiserver.domain.*;
import com.devmate.apiserver.dto.post.request.MentoringRequestDto;
import com.devmate.apiserver.dto.post.request.PostRequestDto;
import com.devmate.apiserver.dto.post.request.RequestDto;
import com.devmate.apiserver.dto.post.request.StudyRequestDto;
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
    public <T extends RequestDto> Long postSave(String loginId, String category, T registerDto){
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new NoSuchElementException("Not Found Member"));
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

    public void deletePost(String loginId, Long postId){
        Post post = postRepository.findPostAndMemberById(postId, loginId).orElseThrow(() ->
                new NoSuchElementException("Not Found Post"));

//        if(!post.getMember().getLoginId().equals(loginId)){
//            throw new LackOfPermissionException("Lack of Permission");
//        }
        postRepository.delete(post);
    }

    public <T extends RequestDto> Long editPost(String loginId, Long postId, T postEditDto){
        Post post = postRepository.findPostAndMemberById(postId, loginId).orElseThrow(() ->
                new NoSuchElementException("Not Found Post"));

//        if(!post.getMember().getLoginId().equals(loginId)){
//            throw new LackOfPermissionException("Lack of Permission");
//        }
        post.getPostHashTag().clear();

        //Todo 해시태그 적용하는 부분 메서드로 추출해 리펙토링 해도 될거같음
        if(!postEditDto.getTags().isEmpty()){
            List<String> tags = postEditDto.getTags();
            for (String tag : tags) {
                Optional<HashTag> findHashtag = hashTagRepository.findByName(tag);
                if(findHashtag.isPresent()){
                    new PostHashTag(post, findHashtag.get());
                }
                else{
                    HashTag hashTag = new HashTag(tag);
                    HashTag savedHashTag = hashTagRepository.save(hashTag);
                    new PostHashTag(post, savedHashTag);
                }
            }
        }
        editPostByCategory(postEditDto, post);
        return post.getId();
    }

    public void addViewCount(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Not Found Post"));
        post.addViewCount();
    }

    private <T extends RequestDto> Post getPostByCategory(Member member, String category, T registerDto){
        Post post;
        if(category.equals("qna")){
            post = new Qna(member,(PostRequestDto) registerDto);
        }
        else if(category.equals("community")){
            post = new Community(member ,(PostRequestDto) registerDto);
        }
        else if(category.equals("job")){
            post = new JobOpening(member,(PostRequestDto) registerDto);
        }
        else if(category.equals("review")){
            post = new Review(member,(PostRequestDto) registerDto);
        }
        else if(category.equals("study")){
            post = new Study(member, (StudyRequestDto) registerDto);
        }
        else if(category.equals("mentoring")){
            post = new Mento(member, (MentoringRequestDto) registerDto);
        }
        else{
            throw new NoSuchElementException("Not Found Category");
        }
        return post;
    }

    private <T extends RequestDto> void editPostByCategory(T postEditDto, Post post) {
        if(post instanceof Qna qna){
            qna.editPost(postEditDto);
        }
        else if(post instanceof Review review){
            review.editPost(postEditDto);
        }
        else if(post instanceof Community community){
            community.editPost(postEditDto);
        }
        else if(post instanceof JobOpening jobOpening){
            jobOpening.editPost(postEditDto);
        }
        else if(post instanceof Study study){
            study.editStudy((StudyRequestDto) postEditDto);
        }
        else if(post instanceof Mento mento){
            mento.editMento((MentoringRequestDto) postEditDto);
        }
    }

}
