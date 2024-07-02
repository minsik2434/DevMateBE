package com.devmate.apiserver.repository;

import com.devmate.apiserver.domain.Comment;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c join fetch c.member cm where c.id = :id")
    Optional<Comment> findCommentAndMemberById(@Param("id") Long id);

    @Query("select c from Comment c join fetch c.member cm where c.post.id = :id order by c.commentDateTime desc")
    List<Comment> findAllCommentAndMemberByPostId(@Param("id") Long id);

    @Query("select c from Comment c join fetch c.member join fetch c.post cp where c.id =:id")
    Optional<Comment> findCommentAndMemberAndPostById(@Param("id") Long id);

    @Query("select count(c) from Comment c where c.post = :post and c.member =:member")
    int countByCommentByMemberPost(@Param("post") Post post, @Param("member") Member member);
}
