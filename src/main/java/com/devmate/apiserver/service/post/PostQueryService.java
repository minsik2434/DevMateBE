package com.devmate.apiserver.service.post;

import com.devmate.apiserver.domain.Mento;
import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.domain.Study;
import com.devmate.apiserver.dto.post.response.MentoringDto;
import com.devmate.apiserver.dto.post.response.PostDto;
import com.devmate.apiserver.dto.post.response.StudyDto;
import com.devmate.apiserver.repository.post.PostRepository;
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
public class PostQueryService {
    private final PostRepository postRepository;

    public PostDto postInfo(Long postId){
        Optional<Post> postAndMemberAndTags = postRepository.findPostAndMemberAndTagsById(postId);
        Post post = postAndMemberAndTags.orElseGet(() -> postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("Not Found Post")));

        return new PostDto(post);
    }

    public StudyDto studyInfo(Long postId){
        Optional<Post> postAndMemberAndTags = postRepository.findPostAndMemberAndTagsById(postId);
        Post post = postAndMemberAndTags.orElseGet(() -> postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("Not Found Post")));
        return new StudyDto((Study) post);
    }

    public MentoringDto mentoringInfo(Long postId){
        Optional<Post> postAndMemberAndTags = postRepository.findPostAndMemberAndTagsById(postId);
        Post post = postAndMemberAndTags.orElseGet(() -> postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("Not Found Post")));
        return new MentoringDto((Mento) post);
    }

}
