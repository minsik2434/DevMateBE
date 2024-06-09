package com.devmate.apiserver.repository.post;

import com.devmate.apiserver.domain.*;
import com.devmate.apiserver.dto.comment.request.CommentRequestDto;
import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import com.devmate.apiserver.repository.CommentRepository;
import com.devmate.apiserver.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    CommentRepository commentRepository;

    List<Qna> initQna = new ArrayList<>();

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void initData(){
        Member member = memberRepository.findById(1L).get();
        for(int i=0; i<10; i++){
            PostRegisterDto postRegisterDto = new PostRegisterDto();
            postRegisterDto.setTitle("qna"+i);
            postRegisterDto.setContent("qna"+i+"번 입니다");
            Qna qna = new Qna(member, postRegisterDto);
            initQna.add(qna);
        }
    }

    @Test
    void findPostAllByParamsTest(){
        HashTag hashTag1 = new HashTag("java");
        HashTag hashTag2 = new HashTag("spring");
        HashTag hashTag3 = new HashTag("jpa");
        hashTagRepository.save(hashTag1);
        hashTagRepository.save(hashTag2);
        hashTagRepository.save(hashTag3);
        for(int i=0; i<initQna.size(); i++){
            if(i%2==0){
                Qna qna = initQna.get(i);
                new PostHashTag(qna,hashTag1);
            }
            else{
                Qna qna = initQna.get(i);
                new PostHashTag(qna, hashTag1);
                new PostHashTag(qna, hashTag2);
                for (int j = 0; j < i; j++) {
                    qna.addViewCount();
                }
            }
        }
        postRepository.saveAll(initQna);

        Pageable pageable = PageRequest.of(0,20);
        Page<Post> result = postRepository.findPostAllByParam("qna", "viewCount", null, new String[]{"spring"}, pageable);

        assertThat(result.getContent()).isNotEmpty();
        assertThat(result.getContent().stream().allMatch(item->item.getClass() == Qna.class)).isTrue();
        result.getContent().forEach(post ->{
            assertThat(post.getPostHashTag().stream().anyMatch(postHashTag -> postHashTag.getHashTag().getName().equals("spring"))).isTrue();
        });

        assertThat(result.getContent().get(0).getTitle()).isEqualTo("qna9");
    }

    @Test
    void findPostAllByMemberFilterParamTest(){
        List<Qna> qnas = postRepository.saveAll(initQna);
        Member member1 = memberRepository.findById(1L).get();
        Member member2 = memberRepository.findById(2L).get();
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setComment("qna 댓글 테스트 입니다");
        Qna qna = qnas.get(3);
        Qna qna1 = qnas.get(4);

        Qna qna2 = qnas.get(7);
        Qna qna3 = qnas.get(8);
        Comment comment = new Comment(member2, qna, commentRequestDto);
        Comment comment1 = new Comment(member2, qna1, commentRequestDto);

        Comment comment2 = new Comment(member1, qna2, commentRequestDto);
        Comment comment3 = new Comment(member1, qna3, commentRequestDto);


        commentRepository.save(comment);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        Pageable pageable = PageRequest.of(0,20);

        //검증 member1이 작성한 게시물들인지 검증
        Page<Post> member1PostsByPost = postRepository.findPostAllByMemberFilterParam(member1.getId(), "post", pageable);
        assertThat(member1PostsByPost.getTotalElements()).isEqualTo(10);
        assertThat(member1PostsByPost.getContent()).containsExactlyInAnyOrderElementsOf(qnas);

        //검증 member1이 댓글단 게시글들인지 검증
        Page<Post> member1PostsByComment = postRepository.findPostAllByMemberFilterParam(member1.getId(), "comment", pageable);
        assertThat(member1PostsByComment.getTotalElements()).isEqualTo(2);
        assertThat(member1PostsByComment.getContent()).containsAll(Arrays.asList(qnas.get(7), qnas.get(8)));

        //검증 member2가 작성한 게시글인지 검증
        Page<Post> member2PostsByPost = postRepository.findPostAllByMemberFilterParam(member2.getId(), "post", pageable);
        assertThat(member2PostsByPost.getTotalElements()).isEqualTo(0);
        assertThat(member2PostsByPost.getContent()).isEmpty();

        Page<Post> member2PostsByComment = postRepository.findPostAllByMemberFilterParam(member2.getId(), "comment", pageable);
        assertThat(member2PostsByComment.getTotalElements()).isEqualTo(2);
        assertThat(member2PostsByComment.getContent()).containsAll(Arrays.asList(qnas.get(3), qnas.get(4)));
    }
}