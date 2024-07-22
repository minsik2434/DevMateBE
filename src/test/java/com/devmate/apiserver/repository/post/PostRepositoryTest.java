package com.devmate.apiserver.repository.post;

import com.devmate.apiserver.domain.*;
import com.devmate.apiserver.dto.comment.request.CommentRequestDto;
import com.devmate.apiserver.dto.post.request.PostRequestDto;
import com.devmate.apiserver.repository.CommentRepository;
import com.devmate.apiserver.repository.GoodRepository;
import com.devmate.apiserver.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Transactional
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    HashTagRepository hashTagRepository;
    @Autowired
    PostHashTagRepository postHashTagRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    GoodRepository goodRepository;

    Member member;
    Post qna;
    Post community;
    Post job;
    PostRequestDto postRequestDto;
    HashTag hashTag;
    PostHashTag qnaHashTag;
    PostHashTag communityHashTag;
    PostHashTag jobHashTag;
    Comment qnaComment;
    CommentRequestDto commentRequestDto;
    Good qnaGood;
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
        qna = new Qna(member, postRequestDto);
        community = new Community(member, postRequestDto);
        job = new JobOpening(member,postRequestDto);

        hashTag = new HashTag("Test");

        qnaHashTag = new PostHashTag(qna,hashTag);
        communityHashTag = new PostHashTag(community,hashTag);
        jobHashTag = new PostHashTag(job,hashTag);


        commentRequestDto = new CommentRequestDto();
        commentRequestDto.setComment(("댓글 내용"));
        qnaComment = new Comment(member,qna,commentRequestDto);

        qnaGood = new Good(member,qna);
    }

    @Test
    @DisplayName("post 아이디로 post조회 (fetch Member and Tags)")
    void findPostByIdTest_fetchMemberAndTags(){

        //Given
        memberRepository.save(member);
        postRepository.save(qna);
        hashTagRepository.save(hashTag);
        postHashTagRepository.save(qnaHashTag);

        //When
        Optional<Post> findPost =
                postRepository.findPostAndMemberAndTagsById(qna.getId());

        //Then
        assertThat(findPost.isPresent()).isTrue();
        assertThat(findPost.get().getId()).isEqualTo(qna.getId());

        assertThat(findPost.get().getPostHashTag()).isNotNull();
        assertThat(findPost.get().getPostHashTag().size()).isGreaterThan(0);

        assertThat(findPost.get().getMember()).isNotNull();
        assertThat(findPost.get().getMember()).isEqualTo(member);
    }

    @Test
    @DisplayName("회원이 작성한 post 아이디로 조회")
    void findPostTestByPostAndMemberIdTest_fetchMember(){
        //Given
        memberRepository.save(member);
        postRepository.save(qna);
        hashTagRepository.save(hashTag);
        postHashTagRepository.save(qnaHashTag);

        //When
        Optional<Post> findPost =
                postRepository.findPostAndMemberById(qna.getId(), member.getLoginId());


        //Then
        assertThat(findPost.isPresent()).isTrue();
        assertThat(findPost.get().getId()).isEqualTo(qna.getId());

        assertThat(findPost.get().getMember()).isNotNull();
        assertThat(findPost.get().getMember()).isEqualTo(member);
    }

    @Test
    @DisplayName("회원이 댓글을 작성한 post 리스트 조회")
    void findPostByMemberCommentTest(){
        //Given
        memberRepository.save(member);
        postRepository.save(qna);
        hashTagRepository.save(hashTag);
        postHashTagRepository.save(qnaHashTag);
        commentRepository.save(qnaComment);

        //When
        List<Post> postList = postRepository.findPostByMemberComment(member);

        //Then
        assertThat(postList).isNotNull();
        assertThat(postList.size()).isGreaterThan(0);
        assertThat(postList.get(0)).isEqualTo(qna);
    }

    @Test
    @DisplayName("회원이 좋아요한 post 리스트 조회")
    void findPostByMemberGoodTest(){
        //Given
        memberRepository.save(member);
        postRepository.save(qna);
        hashTagRepository.save(hashTag);
        postHashTagRepository.save(qnaHashTag);
        goodRepository.save(qnaGood);

        //When
        List<Post> postList = postRepository.findPostsByMemberGood(member);

        //Then
        assertThat(postList).isNotNull();
        assertThat(postList.size()).isGreaterThan(0);
        assertThat(postList.get(0)).isEqualTo(qna);
    }
}