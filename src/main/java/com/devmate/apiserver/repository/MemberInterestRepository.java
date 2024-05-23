package com.devmate.apiserver.repository;

import com.devmate.apiserver.domain.Member;
import com.devmate.apiserver.domain.MemberInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberInterestRepository extends JpaRepository<MemberInterest, Long> {
}
