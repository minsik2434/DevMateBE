package com.devmate.apiserver.repository.post;

import com.devmate.apiserver.domain.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.devmate.apiserver.domain.QComment.comment;
import static com.devmate.apiserver.domain.QGood.*;
import static com.devmate.apiserver.domain.QHashTag.hashTag;
import static com.devmate.apiserver.domain.QMember.member;
import static com.devmate.apiserver.domain.QPost.post;
import static com.devmate.apiserver.domain.QPostHashTag.postHashTag;

@Repository
@Slf4j
public class PostRepositoryImpl implements PostRepositoryCustom{
    QPost qPost = post;
    QMember qMember = member;
    QComment qComment = comment;
    QGood qGood = good;
    QPostHashTag qPostHashTag = postHashTag;
    QHashTag qHashTag = hashTag;
    private final JPAQueryFactory queryFactory;
    public PostRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<Post> findPostAllByParam(String category, String sort, String search, String[] tags, Pageable pageable){

        JPAQuery<Post> baseQuery = queryFactory.selectFrom(post)
                .join(post.member, member).fetchJoin();

        if (!(tags == null || tags.length == 0)) {
            baseQuery.join(post.postHashTag, postHashTag)
                    .join(postHashTag.hashTag, hashTag);
        }
        QueryResults<Post> results = baseQuery
                .where(dTypeEq(category).and(titleOrContentLike(search)), hashTagNameIn(tags))
                .orderBy(getOrderBy(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Post> content = results.getResults();
        long totalCount = results.getTotal();
        return new PageImpl<>(content , pageable, totalCount);
    }

    @Override
    public Page<Post> findPostAllByMemberFilterParam(Long memberId, String type , Pageable pageable){

        QueryResults<Post> results = queryFactory.selectFrom(post)
                .join(post.member, member).fetchJoin()
                .where(filterType(memberId,type))
                .orderBy(post.postingDateTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<Post> content = results.getResults();
        long totalCount = results.getTotal();
        return new PageImpl<>(content,pageable,totalCount);
    }


    private BooleanExpression filterType(Long memberId, String type){
        if(type.equals("post")){
            return post.member.id.eq(memberId);
        }
        else if(type.equals("comment")){
            return post.id.in(
                    JPAExpressions
                            .select(comment.post.id)
                            .from(comment)
                            .where(comment.member.id.eq(memberId))
                            .groupBy(comment.post.id));
        }
        else if(type.equals("good")){
            return post.id.in(
                    JPAExpressions
                            .select(good.post.id)
                            .from(good)
                            .where(good.member.id.eq(memberId))
                            .groupBy(good.post.id));
        }
        return null;
    }

    private BooleanExpression hashTagNameIn(String[] tags) {
        if (tags == null || tags.length == 0) {
            return null;
        }
        return hashTag.name.in(tags);
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
        return post.title.contains(search).or(post.content.contains(search));
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
