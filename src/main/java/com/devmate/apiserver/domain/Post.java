package com.devmate.apiserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Post {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String title;
    private LocalDateTime postingDateTime;
    private Integer viewCount;
    private Integer goodCount;
    private Integer commentCount;
    @Lob
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
