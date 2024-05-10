package com.devmate.apiserver.domain;

import jakarta.persistence.*;

@Entity
public class Good {
    @Id @GeneratedValue
    @Column(name = "good_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
