package com.devmate.apiserver.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostHashTag {
    @Id @GeneratedValue
    @Column(name = "post_hashtag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private HashTag hashTag;

    public PostHashTag(Post post, HashTag hashTag){
        this.post = post;
        this.hashTag = hashTag;
        post.getPostHashTag().add(this);
    }
}
