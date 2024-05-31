package com.devmate.apiserver.dto.post.response;

import com.devmate.apiserver.domain.Study;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StudyDto extends PostDto{

    private Integer recruitCount;
    private String proceed;
    private LocalDateTime deadLine;
    public StudyDto(Study study) {
        super(study);
        this.recruitCount = study.getRecruitCount();
        this.proceed = study.getProceed();
        this.deadLine = study.getDeadLine();
    }
}
