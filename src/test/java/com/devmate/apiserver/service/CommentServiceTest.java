package com.devmate.apiserver.service;

import com.devmate.apiserver.common.exception.LackOfPermissionException;
import com.devmate.apiserver.domain.Comment;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.domain.Qna;
import com.devmate.apiserver.dto.comment.request.CommentRequestDto;
import com.devmate.apiserver.dto.post.request.PostRequestDto;
import com.devmate.apiserver.repository.CommentRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    PostRepository postRepository;
    @InjectMocks
    CommentService commentService;

    @Test
    @DisplayName("댓글 생성시 예외 (없는 회원)")
    void commentSaveTest_NotFoundMember(){
        given(memberRepository.findByLoginId("test")).willReturn(Optional.empty());
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setComment("댓글");
        Throwable throwable = catchThrowable(() -> commentService.commentSave("test", 1L, commentRequestDto));

        assertThat(throwable)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Member");
    }

    @Test
    @DisplayName("댓글 생성시 예외 (없는 게시글)")
    void commentSaveTest_NotFoundPost(){
        Member member = new Member("test","test123","최민식","alstlr",true,"test");
        given(memberRepository.findByLoginId("test")).willReturn(Optional.of(member));
        given(postRepository.findById(1L)).willReturn(Optional.empty());
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setComment("댓글");

        Throwable throwable = catchThrowable(() -> commentService.commentSave("test", 1L, commentRequestDto));
        assertThat(throwable)
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Post");

    }

    @Test
    @DisplayName("댓글 삭제시 예외 (없는 댓글)")
    void commentDeleteTest_NotFoundComment(){
        given(commentRepository.findCommentAndMemberAndPostById(1L)).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> commentService.commentDelete("test", 1L));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Comment");
    }

    @Test
    @DisplayName("댓글 삭제시 예외 (권한오류)")
    void commentDeleteTest_LackOfPermission(){
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setComment("댓글");
        PostRequestDto postRequestDto = new PostRequestDto();
        List<String> tag = new ArrayList<>();
        postRequestDto.setTitle("제목");
        postRequestDto.setContent("내용");
        postRequestDto.setTags(tag);
        Member member = new Member(
                "test",
                "test123",
                "최민식",
                "alstlr",
                true,
                "URL");
        Qna qna = new Qna(member, postRequestDto);
        Comment comment = new Comment(member,qna,commentRequestDto);
        given(commentRepository.findCommentAndMemberAndPostById(1L)).willReturn(Optional.of(comment));

        Throwable throwable = catchThrowable(() -> commentService.commentDelete("other", 1L));
        assertThat(throwable).isInstanceOf(LackOfPermissionException.class)
                .hasMessageContaining("Lack of Permission");
    }

    @Test
    @DisplayName("댓글 수정 예외")
    void commentEditTest(){
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setComment("댓글");
        given(commentRepository.findCommentAndMemberById(1L)).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> commentService.commentEdit("test", 1L, commentRequestDto));
        assertThat(throwable).isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Not Found Comment");
    }


    @Test
    @DisplayName("댓글 수정 예외")
    void commentEditTest_LackOfPermission(){
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setComment("댓글");
        PostRequestDto postRequestDto = new PostRequestDto();
        List<String> tag = new ArrayList<>();
        postRequestDto.setTitle("제목");
        postRequestDto.setContent("내용");
        postRequestDto.setTags(tag);
        Member member = new Member(
                "test",
                "test123",
                "최민식",
                "alstlr",
                true,
                "URL");
        Qna qna = new Qna(member, postRequestDto);
        Comment comment = new Comment(member,qna,commentRequestDto);
        given(commentRepository.findCommentAndMemberById(1L)).willReturn(Optional.of(comment));
        Throwable throwable = catchThrowable(() -> commentService.commentEdit("other", 1L, commentRequestDto));
        assertThat(throwable).isInstanceOf(LackOfPermissionException.class)
                .hasMessageContaining("Lack of Permission");
    }
}