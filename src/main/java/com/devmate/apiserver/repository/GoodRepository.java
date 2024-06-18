package com.devmate.apiserver.repository;

import com.devmate.apiserver.domain.Good;
import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Long> {
    @Query("select g from Good g where g.member = :member and g.post = :post")
    Optional<Good> findGoodByMemberAndPost(@Param("member")Member member, @Param("post") Post post);

    @Query("select g from Good g join fetch g.member join fetch g.post where g.id = :id")
    Optional<Good> findGoodAndMemberAndPostById(@Param("id") Long id);

}
