package io.mhan.springjpatest.posts.repository;

import io.mhan.springjpatest.posts.entity.Post;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface QueryDslPostRepository {
    List<Post> findAll(Sort sort);
}
