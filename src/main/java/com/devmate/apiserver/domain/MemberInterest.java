package com.devmate.apiserver.domain;

import jakarta.persistence.*;

@Entity
public class MemberInterest {
    @Id @GeneratedValue
    @Column(name = "member_Interest_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id")
    private Interest interest;
}
