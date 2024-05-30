package com.devmate.apiserver.repository.post;


import com.devmate.apiserver.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
