package com.devmate.apiserver.service.post;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Post;
import com.devmate.apiserver.dto.post.request.RequestDto;

public interface DtoFactory<T extends RequestDto> {
    Post createPost(Member member, T registerDto);
}
