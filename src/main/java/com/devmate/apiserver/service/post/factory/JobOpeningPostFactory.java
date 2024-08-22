package com.devmate.apiserver.service.post.factory;

import com.devmate.apiserver.domain.JobOpening;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.dto.post.request.PostRequestDto;
import com.devmate.apiserver.service.post.DtoFactory;

public class JobOpeningPostFactory implements DtoFactory<PostRequestDto> {
    @Override
    public Post createPost(Member member, PostRequestDto registerDto) {
        return new JobOpening(member, registerDto);
    }
}
