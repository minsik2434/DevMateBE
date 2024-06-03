package com.devmate.apiserver.repository.post;

import com.devmate.apiserver.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostRepositoryCustom {
    Page<Post> findPostAllByParam(String category,String sort, String search, String[] tags, Pageable pageable);
}
