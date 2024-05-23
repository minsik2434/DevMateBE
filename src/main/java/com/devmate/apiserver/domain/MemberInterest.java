package com.devmate.apiserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    public MemberInterest(Member member, Interest interest){
        this.member = member;
        this.interest = interest;
        member.getMemberInterests().add(this);
    }

}
