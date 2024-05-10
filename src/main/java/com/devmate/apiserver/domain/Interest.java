package com.devmate.apiserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Interest {
    @Id @GeneratedValue
    @Column(name = "interest_id")
    private Long id;

    @Column(name = "interest_name")
    private String name;
}
