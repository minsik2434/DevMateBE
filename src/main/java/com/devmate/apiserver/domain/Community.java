package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.post.request.PostRequestDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("community")
public class Community extends Post{
    public Community(Member member, PostRequestDto postRegisterDto){
        super(member, postRegisterDto);
    }
}
