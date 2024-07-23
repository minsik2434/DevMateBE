package com.devmate.apiserver.service.post;

import com.devmate.apiserver.repository.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostQueryServiceTest {


    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostQueryService postQueryService;

    @Test
    @DisplayName("post 아이디로 조회시 예외")
    void postInfoTest(){
        given(postRepository.findPostAndMemberAndTagsById(1L)).willReturn(Optional.empty());
        given(postRepository.findById(1L)).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> postQueryService.postInfo(1L));

        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Post");
    }
}