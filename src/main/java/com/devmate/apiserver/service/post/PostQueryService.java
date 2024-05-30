package com.devmate.apiserver.service.post;

import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.dto.post.response.PostDto;
import com.devmate.apiserver.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostQueryService {
    private final PostRepository postRepository;

    public PostDto postInfo(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new NoSuchElementException("Post Not Found"));
        PostDto postDto = new PostDto(post);
        return postDto;
    }
}
