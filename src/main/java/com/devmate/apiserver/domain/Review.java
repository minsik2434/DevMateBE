package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("review")
public class Review extends Post{

    public Review(Member member,PostRegisterDto postRegisterDto){
        super(member,postRegisterDto);
    }
}
