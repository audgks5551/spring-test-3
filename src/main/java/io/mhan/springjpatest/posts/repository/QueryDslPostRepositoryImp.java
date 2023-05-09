package io.mhan.springjpatest.posts.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.mhan.springjpatest.posts.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.dsl.Expressions.allOf;
import static com.querydsl.core.types.dsl.Expressions.anyOf;
import static io.mhan.springjpatest.posts.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class QueryDslPostRepositoryImp implements QueryDslPostRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> findAll(Sort sort) {

        // query 만들기
        JPAQuery<Post> contentQuery = jpaQueryFactory
                .select(post)
                .from(post)
                .where(getEq(1L))
                .orderBy(getOrderSpecifiers(sort));

        // query 실행
        List<Post> posts = contentQuery.fetch();

        return posts;
    }

    private static BooleanExpression getEq(Long id) {

        if (id != null && (id.equals("M") || id.equals("W"))) {
            return post.id.eq(id);
        }

        return null;
    }

    // OrderSpecifier 모음
    private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {

        OrderSpecifier[] orderSpecifiers = sort.stream()
                .map(this::createOrderSpecifier)
                .toArray(OrderSpecifier[]::new);
        return orderSpecifiers;
    }

    // Sort의 order -> QueryDsl의 OrderSpecifier
    private OrderSpecifier<?> createOrderSpecifier(Sort.Order o) {
        Order order = o.getDirection().isAscending() ? Order.ASC : Order.DESC;

        Expression<?> expression = switch(o.getProperty()) {
            case "id" -> post.id;
            case "views" -> post.views;
            case "createdAt" -> post.createdAt;
            default -> post.createdAt;
        };

        return new OrderSpecifier(order, expression);
    }
}
