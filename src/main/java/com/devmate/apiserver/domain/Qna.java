package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.post.request.PostRequestDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("qna")
public class Qna extends Post{
    public Qna(Member member, PostRequestDto postRegisterDto) {
        super(member, postRegisterDto);
    }
}
