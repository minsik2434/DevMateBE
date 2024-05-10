package com.devmate.apiserver.domain;

import jakarta.persistence.*;

@Entity
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
}
