package com.devmate.apiserver.repository;

import com.devmate.apiserver.domain.Comment;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.domain.Qna;
import com.devmate.apiserver.dto.comment.request.CommentRequestDto;
import com.devmate.apiserver.dto.post.request.PostRequestDto;
import com.devmate.apiserver.repository.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberRepository memberRepository;

    Post post;
    Member member;
    Comment comment;
    Comment comment2;
    PostRequestDto postRequestDto;
    CommentRequestDto commentRequestDto;
    @BeforeEach
    void init(){
        //Member
        member = new Member(
                "test",
                "test",
                "테스터",
                "닉네임",
                true,
                "testURL");

        //Post
        List<String> list = new ArrayList<>();
        list.add("tag1");
        postRequestDto = new PostRequestDto();
        postRequestDto.setTitle("제목");
        postRequestDto.setContent("내용");
        postRequestDto.setTags(list);
        post = new Qna(member, postRequestDto);

        //Comment
        commentRequestDto = new CommentRequestDto();
        commentRequestDto.setComment("댓글 내용");
        comment = new Comment(member, post, commentRequestDto);
        comment2 = new Comment(member, post, commentRequestDto);

    }


    @Test
    @DisplayName("comment 아이디로 Comment 조회 (fetch Member)")
    void findCommentByIdTest_fetchMember(){
        //Given
        memberRepository.save(member);
        postRepository.save(post);
        commentRepository.save(comment);

        //When
        Optional<Comment> testComment = commentRepository.findCommentAndMemberById(comment.getId());

        //Then
        assertThat(testComment.isPresent()).isTrue();
        assertThat(testComment.get().getId()).isEqualTo(comment.getId());

        Member fetchedMember = testComment.get().getMember();
        assertThat(fetchedMember).isNotNull();

        assertThat(fetchedMember).isEqualTo(comment.getMember());
    }

    @Test
    @DisplayName("comment 아이디로 Comment 조회 (fetch Member, Post)")
    void findCommentByIdTest_fetchMemberAndPost(){
        //Given
        memberRepository.save(member);
        postRepository.save(post);
        commentRepository.save(comment);

        //When
        Optional<Comment> testComment =
                commentRepository.findCommentAndMemberAndPostById(comment.getId());


        //Then
        assertThat(testComment.isPresent()).isTrue();
        assertThat(testComment.get().getId()).isEqualTo(comment.getId());

        Member fetchedMember = testComment.get().getMember();
        assertThat(fetchedMember).isNotNull();
        assertThat(fetchedMember).isEqualTo(comment.getMember());

        Post fetchedPost = testComment.get().getPost();
        assertThat(fetchedPost).isNotNull();
        assertThat(fetchedPost).isEqualTo(post);
    }

    @Test
    @DisplayName("회원이 작성한 댓글 갯수")
    void countCommentByMemberPost(){
        //Given
        memberRepository.save(member);
        postRepository.save(post);
        commentRepository.save(comment);

        //When
        int count = commentRepository.countByCommentByMemberPost(post, member);

        //Then
        assertThat(count).isGreaterThan(0);

    }

    @Test
    @DisplayName("post 아이디로 Comment 리스트 조회 (fetch Member)")
    void findAllCommentTest_fetchMember(){
        //Given
        memberRepository.save(member);
        postRepository.save(post);
        commentRepository.save(comment);
        commentRepository.save(comment2);

        //When
        List<Comment> commentList =
                commentRepository.findAllCommentAndMemberByPostId(post.getId());

        //Then
        assertThat(commentList).isNotNull();
        assertThat(commentList.size()).isGreaterThan(0);

        Member fetchedMember = commentList.get(0).getMember();
        assertThat(fetchedMember).isNotNull();
        assertThat(fetchedMember).isEqualTo(member);
    }

}