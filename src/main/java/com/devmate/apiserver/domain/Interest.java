package com.devmate.apiserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Interest {
    @Id @GeneratedValue
    @Column(name = "interest_id")
    private Long id;

    @Column(name = "interest_name")
    private String name;

    public Interest(String name){
        this.name = name;
    }
}
