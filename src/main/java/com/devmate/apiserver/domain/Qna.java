package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("qna")
public class Qna extends Post{
    public Qna(Member member, PostRegisterDto postRegisterDto){
        super(member,postRegisterDto);
    }
}
