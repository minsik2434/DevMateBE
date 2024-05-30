package com.devmate.apiserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag {
    @Id @GeneratedValue
    @Column(name = "hashtag_id")
    private Long Id;

    @Column(name = "hashtag_name")
    private String name;

    public HashTag(String name){
        this.name = name;
    }
}
