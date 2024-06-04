package com.devmate.apiserver.repository.post;

import com.devmate.apiserver.domain.*;
import com.devmate.apiserver.dto.post.request.MentoringRegisterDto;
import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import com.devmate.apiserver.dto.post.request.StudyRegisterDto;
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
}