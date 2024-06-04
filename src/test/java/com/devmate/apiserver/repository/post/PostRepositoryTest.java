package com.devmate.apiserver.repository.post;

import com.devmate.apiserver.domain.*;
import com.devmate.apiserver.dto.post.request.MentoringRegisterDto;
import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import com.devmate.apiserver.dto.post.request.StudyRegisterDto;
import com.devmate.apiserver.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

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


    @BeforeEach
    void initData(){
        Member member = memberRepository.findById(1L).get();
        PostRegisterDto postRegisterDto = new PostRegisterDto();
        StudyRegisterDto studyRegisterDto = new StudyRegisterDto();
        MentoringRegisterDto mentoringRegisterDto = new MentoringRegisterDto();
        HashTag hashTag1 = new HashTag("java");
        HashTag hashTag2 = new HashTag("spring");
        HashTag hashTag3 = new HashTag("data");
        List<HashTag> hashTags = new ArrayList<>();
        hashTags.add(hashTag1);
        hashTags.add(hashTag2);
        hashTags.add(hashTag3);
        hashTagRepository.saveAll(hashTags);
        List<Qna> qnas = new ArrayList<>();
        List<Study> studys = new ArrayList<>();
        List<Mento> mentos = new ArrayList<>();
        int k = 0;
        for (int i = 0; i <3; i++) {
            for (int j = 0; j < 10; j++) {
                postRegisterDto.setTitle("테스트" + k + "(Qna)");
                if (k < 10) {
                    postRegisterDto.setContent("자바 질문");
                    Qna qna = new Qna(member, postRegisterDto);
                    new PostHashTag(qna, hashTag1);
                    qnas.add(qna);
                }
                else if (k>10 && k<20){
                    postRegisterDto.setContent("파이썬 질문");
                    Qna qna = new Qna(member, postRegisterDto);
                    new PostHashTag(qna, hashTag2);
                    qnas.add(qna);
                }
                else {
                    postRegisterDto.setContent("C 언어 질문");
                    Qna qna = new Qna(member, postRegisterDto);
                    new PostHashTag(qna, hashTag1);
                    new PostHashTag(qna, hashTag2);
                    qnas.add(qna);
                }
                k++;
            }
        }
        k=0;
        for(int i=0; i<3; i++){
            for (int j = 0; j < 10; j++) {
                studyRegisterDto.setTitle("테스트"+k+"(Study)");
                studyRegisterDto.setContent("Study"+k+"번째");
                if (k < 10) {
                    studyRegisterDto.setProceed("오프라인");
                    studyRegisterDto.setRecruitCount(4);
                    studyRegisterDto.setDeadLine(LocalDateTime.now());
                    Study study = new Study(member, studyRegisterDto);
                    new PostHashTag(study, hashTag1);
                    studys.add(study);
                }
                else if (k>10 && k<20){
                    studyRegisterDto.setProceed("온라인");
                    studyRegisterDto.setRecruitCount(2);
                    studyRegisterDto.setDeadLine(LocalDateTime.now());
                    Study study = new Study(member, studyRegisterDto);
                    new PostHashTag(study, hashTag2);
                    studys.add(study);
                }
                else {
                    studyRegisterDto.setProceed("온/오프라인");
                    studyRegisterDto.setRecruitCount(5);
                    studyRegisterDto.setDeadLine(LocalDateTime.now());
                    Study study = new Study(member, studyRegisterDto);
                    new PostHashTag(study, hashTag2);
                    new PostHashTag(study, hashTag1);
                    studys.add(study);
                }
                k++;
            }
        }

        k=0;
        for(int i=0; i<3; i++){
            for (int j = 0; j < 10; j++) {
                mentoringRegisterDto.setTitle("테스트"+k+"(mentoring)");
                if (k < 10) {
                    mentoringRegisterDto.setContent("자바 프로그래밍 멘토링");
                    mentoringRegisterDto.setCareer(4);
                    mentoringRegisterDto.setEmail("test" + k + "@naver.com");
                    mentoringRegisterDto.setJob("FrontEnd");
                    mentoringRegisterDto.setPhoneNumber("010-1234-5678");
                    mentoringRegisterDto.setGithubUrl("test" + k + "@github.com");
                    Mento mento = new Mento(member, mentoringRegisterDto);
                    new PostHashTag(mento, hashTag1);
                    mentos.add(mento);
                }
                else if (k>10 && k<20){
                    mentoringRegisterDto.setContent("파이썬 프로그래밍 멘토링");
                    mentoringRegisterDto.setCareer(3);
                    mentoringRegisterDto.setEmail("test" + k + "@naver.com");
                    mentoringRegisterDto.setJob("BackEnd");
                    mentoringRegisterDto.setPhoneNumber("010-1234-5678");
                    mentoringRegisterDto.setGithubUrl("test" + k + "@github.com");
                    Mento mento = new Mento(member, mentoringRegisterDto);
                    new PostHashTag(mento, hashTag2);
                    mentos.add(mento);
                }
                else {
                    mentoringRegisterDto.setContent("C언어 프로그래밍 멘토링");
                    mentoringRegisterDto.setCareer(3);
                    mentoringRegisterDto.setEmail("test" + k + "@naver.com");
                    mentoringRegisterDto.setJob("BackEnd");
                    mentoringRegisterDto.setPhoneNumber("010-1234-5678");
                    mentoringRegisterDto.setGithubUrl("test" + k + "@github.com");
                    Mento mento = new Mento(member, mentoringRegisterDto);
                    new PostHashTag(mento, hashTag2);
                    new PostHashTag(mento, hashTag1);
                    mentos.add(mento);
                }
                k++;
            }
        }

        postRepository.saveAll(qnas);
        postRepository.saveAll(studys);
        postRepository.saveAll(mentos);
    }

    @Test
    void findPostAllByParamsTest(){
        Pageable pageable = PageRequest.of(0,20);
        HashTag hashTag = hashTagRepository.findByName("data").get();
        Page<Post> qna = postRepository.findPostAllByParam("qna", null, "자바", new String[]{"java"}, pageable);
        assertThat(qna.getContent()).allMatch(item -> item instanceof Qna);
        assertThat(qna.getContent()).allMatch(item -> item.getContent().contains("자바"));

    }
}