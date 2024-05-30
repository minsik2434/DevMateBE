package com.devmate.apiserver.repository.post;


import com.devmate.apiserver.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p join fetch p.member where p.id = :id")
    Post findPostAndMemberAndTagsById(@Param("id") Long id);
}
