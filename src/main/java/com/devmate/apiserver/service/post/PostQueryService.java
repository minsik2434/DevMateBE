package com.devmate.apiserver.service.post;

import com.devmate.apiserver.domain.*;
import com.devmate.apiserver.dto.post.response.MentoringDto;
import com.devmate.apiserver.dto.post.response.PostDto;
import com.devmate.apiserver.dto.post.response.StudyDto;
import com.devmate.apiserver.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        return getPostDtoByType(post);
    }

    public Page<PostDto> postsFilterParam(String category, String sort, String search, String[] tags, int page){
        Pageable pageable = PageRequest.of(page, 20);
        Page<Post> postAllByParam = postRepository.findPostAllByParam(category, sort, search, tags, pageable);
        return postAllByParam.map(this::getPostDtoByType);
    }


    private PostDto getPostDtoByType(Post post){
        PostDto postDto = null;
        if(post instanceof Qna || post instanceof Review || post instanceof Community || post instanceof JobOpening){
            postDto = new PostDto(post);
        }
        else if(post instanceof Study){
            postDto = new StudyDto((Study) post);
        }
        else if(post instanceof Mento){
            postDto = new MentoringDto((Mento) post);
        }
        return postDto;
    }
}
