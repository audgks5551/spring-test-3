package io.mhan.springjpatest.posts.repository;

import io.mhan.springjpatest.posts.entity.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
