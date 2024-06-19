package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.post.request.StudyRequestDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Slf4j
@DiscriminatorValue("study")
public class Study extends Post{
    private Integer recruitCount;
    private String proceed;
    private LocalDateTime deadLine;

    public Study(Member member, StudyRequestDto studyRegisterDto){
        super(member, studyRegisterDto);
        this.recruitCount = studyRegisterDto.getRecruitCount();
        log.info("studyRegisterDto.getRecruitCount() ={}", studyRegisterDto.getRecruitCount());
        this.proceed = studyRegisterDto.getProceed();
        this.deadLine = studyRegisterDto.getDeadLine();
    }

    public void editStudy(StudyRequestDto studyEditDto) {
        super.editPost(studyEditDto);
        this.recruitCount = studyEditDto.getRecruitCount();
        this.proceed = studyEditDto.getProceed();
        this.deadLine = studyEditDto.getDeadLine();
    }
}
