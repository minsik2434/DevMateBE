package com.devmate.apiserver.service.post.factory;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.domain.Study;
import com.devmate.apiserver.dto.post.request.StudyRequestDto;
import com.devmate.apiserver.service.post.DtoFactory;

public class StudyPostFactory implements DtoFactory<StudyRequestDto> {
    @Override
    public Post createPost(Member member, StudyRequestDto registerDto) {
        return new Study(member, registerDto);
    }
}
