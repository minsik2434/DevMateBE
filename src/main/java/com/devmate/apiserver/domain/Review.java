package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.post.request.PostRequestDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("review")
public class Review extends Post{

    public Review(Member member, PostRequestDto postRegisterDto){
        super(member,postRegisterDto);
    }
}
