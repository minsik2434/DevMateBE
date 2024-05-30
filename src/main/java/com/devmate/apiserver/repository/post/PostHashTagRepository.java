package com.devmate.apiserver.repository.post;

import com.devmate.apiserver.domain.PostHashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashTagRepository extends JpaRepository<PostHashTag, Long> {
}
