package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.post.request.StudyRegisterDto;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Study extends Post{
    private Integer recruitCount;
    private String proceed;
    private LocalDateTime deadLine;
}
