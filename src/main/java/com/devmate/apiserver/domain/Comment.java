package com.devmate.apiserver.domain;

import com.devmate.apiserver.dto.comment.request.CommentRegisterDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String commentBody;
    private LocalDateTime commentDateTime;

    public Comment(Member member, Post post, CommentRegisterDto commentRegisterDto){
        this.member = member;
        this.post = post;
        this.commentBody = commentRegisterDto.getComment();
        this.commentDateTime = LocalDateTime.now();
    }
}
