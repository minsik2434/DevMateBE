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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    Post qna2;
    Post community;
    Post job;
    PostRequestDto postRequestDto;
    HashTag hashTag1;
    HashTag hashTag2;
    HashTag hashTag3;
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
        qna2 = new Qna(member, postRequestDto);
        community = new Community(member, postRequestDto);
        job = new JobOpening(member,postRequestDto);

        hashTag1 = new HashTag("tag1");
        hashTag2 = new HashTag("tag2");
        hashTag3 = new HashTag("tag3");

        qnaHashTag = new PostHashTag(qna, hashTag1);
        communityHashTag = new PostHashTag(community, hashTag2);
        jobHashTag = new PostHashTag(job, hashTag3);


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
        hashTagRepository.save(hashTag1);
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
        hashTagRepository.save(hashTag1);
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
        hashTagRepository.save(hashTag1);
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
        hashTagRepository.save(hashTag1);
        postHashTagRepository.save(qnaHashTag);
        goodRepository.save(qnaGood);

        //When
        List<Post> postList = postRepository.findPostsByMemberGood(member);

        //Then
        assertThat(postList).isNotNull();
        assertThat(postList.size()).isGreaterThan(0);
        assertThat(postList.get(0)).isEqualTo(qna);
    }

    @Test
    @DisplayName("카테고리별 post 리스트 조회")
    void findPostAllByCategoryTest(){
        //Given
        memberRepository.save(member);
        hashTagRepository.save(hashTag1);
        hashTagRepository.save(hashTag2);
        //qna
        postRepository.save(qna);
        postHashTagRepository.save(qnaHashTag);

        //community
        postRepository.save(community);
        postHashTagRepository.save(communityHashTag);

        Pageable pageable = PageRequest.of(0,20);

        //When
        Page<Post> qnaList =
                postRepository.findPostAllByParam("qna", "latest", null, null, pageable);
        Page<Post> communityList =
                postRepository.findPostAllByParam("community", "latest", null, null, pageable);


        //Then
        assertThat(qnaList).isNotNull();
        assertThat(qnaList).isNotEmpty();
        assertThat(qnaList.getContent().get(0)).isEqualTo(qna);
        assertThat(qnaList.getContent().get(0).getDType()).isEqualTo(qna.getDType());

        assertThat(communityList).isNotNull();
        assertThat(communityList).isNotEmpty();
        assertThat(communityList.getContent().get(0)).isEqualTo(community);
        assertThat(communityList.getContent().get(0).getDType()).isEqualTo(community.getDType());
    }

    @Test
    @DisplayName("postList 정렬테스트")
    void findPostAllByOrderTest(){
        //Given
        memberRepository.save(member);
        hashTagRepository.save(hashTag1);
        qna.addViewCount();
        qna.addGoodCount();
        postRepository.save(qna);
        postRepository.save(qna2);
        Pageable pageable = PageRequest.of(0,20);

        //When
        Page<Post> latestList = postRepository.findPostAllByParam("qna", "latest", null, null, pageable);
        Page<Post> viewList = postRepository.findPostAllByParam("qna", "view", null, null, pageable);
        Page<Post> goodList = postRepository.findPostAllByParam("qna", "good", null, null, pageable);

        //Then
        assertThat(latestList.getTotalElements()).isEqualTo(2);
        assertThat(latestList.getContent().get(0)).isEqualTo(qna2);
        assertThat(latestList.getContent().get(1)).isEqualTo(qna);

        assertThat(viewList.getTotalElements()).isEqualTo(2);
        assertThat(viewList.getContent().get(0)).isEqualTo(qna);
        assertThat(viewList.getContent().get(1)).isEqualTo(qna2);

        assertThat(goodList.getTotalElements()).isEqualTo(2);
        assertThat(goodList.getContent().get(0)).isEqualTo(qna);
        assertThat(goodList.getContent().get(1)).isEqualTo(qna2);
    }

    @Test
    @DisplayName("tag조회 테스트")
    void findPostAllTagTest(){
        //Given
        memberRepository.save(member);
        hashTagRepository.save(hashTag1);
        hashTagRepository.save(hashTag2);
        postRepository.save(qna);
        postRepository.save(qna2);
        Pageable pageable =PageRequest.of(0,20);

        //When
        String[] tagList = {"tag1"};
        Page<Post> postList =
                postRepository.findPostAllByParam("qna", "latest", null, tagList, pageable);

        //Then
        assertThat(postList.getTotalElements()).isEqualTo(1);
        List<PostHashTag> postHashTagList = postList.getContent().get(0).getPostHashTag();
        assertThat(postHashTagList)
                .extracting(PostHashTag::getHashTag)
                .contains(hashTag1);

    }

    @Test
    @DisplayName("post 검색으로 조회")
    void findPostAllSearchTest(){
        //Given
        memberRepository.save(member);
        hashTagRepository.save(hashTag1);
        postRepository.save(qna);
        Pageable pageable =PageRequest.of(0,20);

        //When
        Page<Post> searchList =
                postRepository.findPostAllByParam("qna", "latest", "제목", null, pageable);

        //Then
        assertThat(searchList.getTotalElements()).isEqualTo(1);
        assertThat(searchList.getContent().get(0).getTitle()).isEqualTo("제목");
    }

    @Test
    @DisplayName("회원 관련 post 조회")
    void findPostMemberTest(){
        //Given
        memberRepository.save(member);
        hashTagRepository.save(hashTag1);
        postRepository.save(qna);
        Pageable pageable =PageRequest.of(0,20);

        //When
        Page<Post> postList = postRepository.findPostAllByMemberFilterParam(member.getId(), "post", pageable);

        //Then
        assertThat(postList).isNotEmpty();
        assertThat(postList.getContent().get(0)).isEqualTo(qna);
    }
}