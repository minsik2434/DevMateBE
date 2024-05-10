package com.devmate.apiserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class HashTag {
    @Id @GeneratedValue
    @Column(name = "hashtag_id")
    private Long Id;

    @Column(name = "hashtag_name")
    private String name;
}
