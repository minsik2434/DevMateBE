package com.devmate.apiserver.repository;

import com.devmate.apiserver.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c join fetch c.member cm where c.id = :id")
    Optional<Comment> findCommentAndMemberById(@Param("id") Long id);
}
