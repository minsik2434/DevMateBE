package com.devmate.apiserver.service;

import com.devmate.apiserver.common.exception.DuplicateResourceException;
import com.devmate.apiserver.common.exception.LackOfPermissionException;
import com.devmate.apiserver.domain.Good;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Qna;
import com.devmate.apiserver.dto.post.request.PostRequestDto;
import com.devmate.apiserver.repository.GoodRepository;
import com.devmate.apiserver.repository.MemberRepository;
import com.devmate.apiserver.repository.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GoodServiceTest {

    @Mock
    GoodRepository goodRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    GoodService goodService;

    @Test
    @DisplayName("좋아요 조회시 예외 (없는 회원)")
    void findGoodTest_NotFoundMember(){
        given(memberRepository.findByLoginId("test")).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> goodService.findGood("test", 1L));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Member");
    }
    @Test
    @DisplayName("좋아요 조회시 예외 (없는 게시글)")
    void findGoodTest_NotFoundPost(){
        Member member = new Member(
                "test",
                "test123",
                "최민식",
                "alstlr",
                true,
                "URL");

        given(memberRepository.findByLoginId("test")).willReturn(Optional.of(member));
        given(postRepository.findById(1L)).willReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> goodService.findGood("test", 1L));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Post");
    }

    @Test
    @DisplayName("좋아요 생성시 예외 (없는 회원)")
    void goodSaveTest_NotFoundMember(){
        given(memberRepository.findByLoginId("test")).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> goodService.goodSave("test", 1L));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Member");
    }

    @Test
    @DisplayName("좋아요 생성시 예외 (없는 게시글)")
    void goodSaveTest_NotFoundPost(){
        Member member = new Member(
                "test",
                "test123",
                "최민식",
                "alstlr",
                true,
                "URL");

        given(memberRepository.findByLoginId("test")).willReturn(Optional.of(member));
        given(postRepository.findById(1L)).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> goodService.goodSave("test", 1L));
        assertThat(throwable)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Post");
    }

    @Test
    @DisplayName("좋아요 생성시 이미 존재하는 좋아요인경우")
    void goodSaveTest_Duplicated(){
        PostRequestDto postRequestDto = new PostRequestDto();
        List<String> list = new ArrayList<>();
        postRequestDto.setTitle("제목");
        postRequestDto.setContent("내용");
        postRequestDto.setTags(list);
        Member member = new Member(
                "test",
                "test123",
                "최민식",
                "alstlr",
                true,
                "URL");

        Qna qna = new Qna(member, postRequestDto);
        Good good = new Good(member,qna);

        given(memberRepository.findByLoginId("test")).willReturn(Optional.of(member));
        given(postRepository.findById(1L)).willReturn(Optional.of(qna));
        given(goodRepository.findGoodByMemberAndPost(member, qna)).willReturn(Optional.of(good));
        Throwable throwable = catchThrowable(() -> goodService.goodSave("test", 1L));
        assertThat(throwable).isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("is Exists Good");
    }

    @Test
    @DisplayName("좋아요 삭제시 예외 (없는 회원)")
    void goodDeleteTest_NotFoundMember(){
        given(memberRepository.findByLoginId("test")).willReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> goodService.goodDelete("test", 1L));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Member");
    }

    @Test
    @DisplayName("좋아요 삭제시 예외 (없는 좋아요)")
    void goodDeleteTest_NotFoundGood(){
        Member member = new Member(
                "test",
                "test123",
                "최민식",
                "alstlr",
                true,
                "URL");

        given(memberRepository.findByLoginId("test")).willReturn(Optional.of(member));
        given(goodRepository.findGoodAndMemberAndPostById(1L)).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> goodService.goodDelete("test", 1L));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Good");
    }
}