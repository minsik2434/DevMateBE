package com.devmate.apiserver.repository.post;


import com.devmate.apiserver.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    @Query("select p from Post p join fetch p.member join fetch p.postHashTag ph join fetch ph.hashTag where p.id = :id")
    Optional<Post> findPostAndMemberAndTagsById(@Param("id") Long id);
}
