package com.devmate.apiserver.domain;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Study extends Post{
    private Integer recruitCount;
    private String proceed;
    private LocalDateTime deadLine;
}
