package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.post.request.PostRegisterDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    public Post(Member member, PostRegisterDto postRegisterDto){
        this.title = postRegisterDto.getTitle();
        this.postingDateTime = LocalDateTime.now();
        this.viewCount = 0;
        this.goodCount = 0;
        this.commentCount = 0;
        this.body = postRegisterDto.getContent();
        this.member = member;
    }
}
