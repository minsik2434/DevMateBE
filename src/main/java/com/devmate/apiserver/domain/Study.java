package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import com.devmate.apiserver.dto.post.request.StudyRegisterDto;
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
public class Study extends Post{
    private Integer recruitCount;
    private String proceed;
    private LocalDateTime deadLine;

    public Study(Member member, StudyRegisterDto studyRegisterDto){
        super(member, studyRegisterDto);
        this.recruitCount = studyRegisterDto.getRecruitCount();
        log.info("studyRegisterDto.getRecruitCount() ={}", studyRegisterDto.getRecruitCount());
        this.proceed = studyRegisterDto.getProceed();
        this.deadLine = studyRegisterDto.getDeadLine();
    }
}
