package com.devmate.apiserver.service.post.factory;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Mento;
import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.dto.post.request.MentoringRequestDto;
import com.devmate.apiserver.service.post.DtoFactory;

public class MentoringPostFactory implements DtoFactory<MentoringRequestDto> {
    @Override
    public Post createPost(Member member, MentoringRequestDto registerDto) {
        return new Mento(member, registerDto);
    }
}
