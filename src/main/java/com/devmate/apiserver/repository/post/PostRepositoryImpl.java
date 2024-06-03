package com.devmate.apiserver.repository.post;

import com.devmate.apiserver.domain.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.devmate.apiserver.domain.QHashTag.hashTag;
import static com.devmate.apiserver.domain.QMember.member;
import static com.devmate.apiserver.domain.QPost.post;
import static com.devmate.apiserver.domain.QPostHashTag.postHashTag;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom{
    QPost qPost = post;
    QMember qMember = member;
    QPostHashTag qPostHashTag = postHashTag;
    QHashTag qHashTag = hashTag;
    private final JPAQueryFactory queryFactory;
    public PostRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<Post> findPostAllByParam(String category, String sort, String search, String[] tags, Pageable pageable){

        QueryResults<Post> results = queryFactory.selectFrom(post)
                .join(post.member, member).fetchJoin()
                .where(dTypeEq(category).and(titleOrContentLike(search)).and(tagFiltering(tags)))
                .orderBy(getOrderBy(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Post> content = results.getResults();
        long totalCount = results.getTotal();
        return new PageImpl<>(content , pageable, totalCount);
    }

    private BooleanExpression tagFiltering(String[] tags) {
        BooleanExpression tagCondition = null;
        if (tags != null && tags.length > 0) {
            tagCondition = post.id.in(
                    JPAExpressions
                            .select(postHashTag.post.id)
                            .from(postHashTag)
                            .join(postHashTag.hashTag, hashTag)
                            .where(hashTag.name.in(tags))
            );
        }
        return tagCondition;
    }

    private BooleanExpression dTypeEq(String category){
        if(category == null){
            return null;
        }
        return post.dType.eq(category);
    }

    private BooleanExpression titleOrContentLike(String search){
        if(search == null || search.isBlank()){
            return null;
        }
        return post.title.contains(search).or(post.content.like(search));
    }
    private OrderSpecifier<?>[] getOrderBy(String sort) {
        OrderSpecifier<?>[] orders;
        if ("good".equals(sort)) {
            orders = new OrderSpecifier<?>[]{post.goodCount.desc()};
        } else if ("comment".equals(sort)) {
            orders = new OrderSpecifier<?>[]{post.commentCount.desc()};
        } else if("view".equals(sort)){
            orders = new OrderSpecifier<?>[]{post.viewCount.desc()};
        }
        else{
            orders = new OrderSpecifier<?>[]{post.postingDateTime.desc()};
        }
        return orders;
    }
}
