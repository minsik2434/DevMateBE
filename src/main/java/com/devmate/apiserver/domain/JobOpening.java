package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobOpening extends Post{

    public JobOpening(Member member, PostRegisterDto postRegisterDto){
        super(member, postRegisterDto);
    }
}
