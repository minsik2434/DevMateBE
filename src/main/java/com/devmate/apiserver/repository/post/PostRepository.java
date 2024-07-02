package com.devmate.apiserver.repository.post;


import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    @Query("select p from Post p join fetch p.member join fetch p.postHashTag ph join fetch ph.hashTag where p.id = :id")
    Optional<Post> findPostAndMemberAndTagsById(@Param("id") Long id);

    @Query("select p from Post p join fetch p.member where p.id = :id and p.member.loginId =:loginId")
    Optional<Post> findPostAndMemberById(@Param("id") Long id, @Param("loginId") String loginId);

    @Query("select p from Post p where p.id in (select c.post.id from Comment c where c.member = :member)")
    List<Post> findPostByMemberComment(@Param("member") Member member);

    @Query("select p from Post p where p.id in (select g.post.id from Good g where g.member = :member)")
    List<Post> findPostsByMemberGood(@Param("member") Member member);
}
