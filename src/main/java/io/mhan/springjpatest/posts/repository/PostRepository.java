package io.mhan.springjpatest.posts.repository;

import io.mhan.springjpatest.posts.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
}
