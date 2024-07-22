package com.devmate.apiserver.repository;

import com.devmate.apiserver.domain.Good;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.domain.Qna;
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
class GoodRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    GoodRepository goodRepository;
    @Autowired
    PostRepository postRepository;

    Post post;
    Member member;
    PostRequestDto postRequestDto;
    Good good;

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

        good = new Good(member, post);
    }

    @Test
    @DisplayName("post에 회원이 작성한 good 조회")
    void findGoodByMemberAndPostTest(){
        //Given
        memberRepository.save(member);
        postRepository.save(post);
        goodRepository.save(good);

        //When
        Optional<Good> goodTest = goodRepository.findGoodByMemberAndPost(member, post);

        //Then
        assertThat(goodTest.isPresent()).isTrue();
        assertThat(goodTest.get().getId()).isEqualTo(good.getId());

    }

    @Test
    @DisplayName("post 아이디로 good 조회")
    void findGoodByPostId_fetchMemberAndPost(){
        //Given
        memberRepository.save(member);
        postRepository.save(post);
        goodRepository.save(good);

        //When
        Optional<Good> goodTest = goodRepository.findGoodAndMemberAndPostById(good.getId());

        //Then
        assertThat(goodTest.isPresent()).isTrue();
        assertThat(goodTest.get().getId()).isEqualTo(good.getId());

        assertThat(goodTest.get().getMember()).isNotNull();
        assertThat(goodTest.get().getPost()).isNotNull();
        assertThat(goodTest.get().getPost().getId()).isEqualTo(post.getId());
    }

}