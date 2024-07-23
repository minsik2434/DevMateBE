package com.devmate.apiserver.service.post;

import com.devmate.apiserver.dto.post.request.PostRequestDto;
import com.devmate.apiserver.dto.post.request.RequestDto;
import com.devmate.apiserver.repository.MemberRepository;
import com.devmate.apiserver.repository.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    PostRepository postRepository;
    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    PostService postService;

    @Test
    @DisplayName("post 저장시 예외")
    void saveExceptionTest(){
        PostRequestDto requestDto = new PostRequestDto();
        List<String> tags = new ArrayList<>();
        requestDto.setTitle("제목");
        requestDto.setContent("내용");
        requestDto.setTags(tags);
        given(memberRepository.findByLoginId("test")).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> postService.postSave("test","qna",requestDto));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class).hasMessageContaining("Not Found Member");
    }

    @Test
    @DisplayName("post 삭제시 예외")
    void deleteExceptionTest(){
        given(postRepository.findPostAndMemberById(1L,"test")).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> postService.deletePost("test", 1L));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class).hasMessageContaining("Not Found Post");
    }

    @Test
    @DisplayName("post 수정시 예외")
    void editExceptionTest(){
        PostRequestDto requestDto = new PostRequestDto();
        List<String> tags = new ArrayList<>();
        requestDto.setTitle("제목");
        requestDto.setContent("내용");
        requestDto.setTags(tags);
        given(postRepository.findPostAndMemberById(1L,"test")).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> postService.editPost("test",1L,requestDto));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class).hasMessageContaining("Not Found Post");
    }

    @Test
    @DisplayName("조회수 증가시 예외")
    void addViewCountExceptionTest(){
        given(postRepository.findById(1L)).willReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> postService.addViewCount(1L));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class).hasMessageContaining("Not Found Post");
    }
}