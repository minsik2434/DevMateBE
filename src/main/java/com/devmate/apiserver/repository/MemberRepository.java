package com.devmate.apiserver.repository;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.MemberInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    Optional<Member> findByNickName(String nickName);

    @Query("select m.memberInterests from Member m where m.id = :memberId")
    List<MemberInterest> findMemberInterests(@Param("memberId") Long memberId);

    @Query("select distinct m from Member m join fetch m.memberInterests memberInterest " +
            "join fetch memberInterest.interest where m.loginId = :loginId")
    Optional<Member> findMemberAndInterestsByLoginId(@Param("loginId") String loginId);

    @Query("select distinct m from Member m join fetch m.memberInterests memberInterest " +
            "join fetch memberInterest.interest where m.id = :id")
    Optional<Member> findMemberAndInterestsById(@Param("id") Long id);
}
