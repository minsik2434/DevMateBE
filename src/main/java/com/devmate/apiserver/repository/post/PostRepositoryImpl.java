package com.devmate.apiserver.repository.post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    public PostRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
}
