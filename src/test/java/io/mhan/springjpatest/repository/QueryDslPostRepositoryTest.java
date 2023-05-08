package io.mhan.springjpatest.repository;

import io.mhan.springjpatest.posts.entity.Post;
import io.mhan.springjpatest.posts.repository.PostRepository;
import io.mhan.springjpatest.posts.repository.QueryDslPostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QueryDslPostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private QueryDslPostRepository queryDslPostRepository;

    @BeforeAll
    void beforeAll() {
        Random random = new Random();
        for (int i=1; i<=15; i++) {
            Post post = Post.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .views(random.nextLong(101))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            postRepository.save(post);
        }
    }

    @AfterAll
    void afterAll() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("모든 post 조회")
    void t1() {
        List<Post> posts = queryDslPostRepository.findAll(Sort.unsorted());

        assertThat(posts.size()).isEqualTo(15);
    }

    @Test
    @DisplayName("오래된 순 조회")
    void t2() {
        Sort.Order order = Sort.Order.asc("id");

        Sort sort = Sort.by(order);

        List<Post> posts = queryDslPostRepository.findAll(sort);

        assertThat(posts).isSortedAccordingTo(Comparator.comparing(Post::getId));
    }

    @Test
    @DisplayName("최신순 조회")
    void t3() {
        Sort.Order order = Sort.Order.desc("createdAt");

        Sort sort = Sort.by(order);

        List<Post> posts = queryDslPostRepository.findAll(sort);
        Collections.reverse(posts);

        assertThat(posts).isSortedAccordingTo(Comparator.comparing(Post::getCreatedAt));
    }

    @Test
    @DisplayName("아무 정렬 조건을 주지 않았을 때")
    void t4() {
        List<Post> posts = queryDslPostRepository.findAll(Sort.unsorted());

        assertThat(posts).isSortedAccordingTo(Comparator.comparing(Post::getId));
    }

    @Test
    @DisplayName("가장 조회수가 많은순으로 정렬")
    void t5() {
        Sort.Order order = Sort.Order.desc("views");

        Sort sort = Sort.by(order);
        List<Post> posts = queryDslPostRepository.findAll(sort);

        Collections.reverse(posts);

        assertThat(posts).isSortedAccordingTo(Comparator.comparing(Post::getViews));
    }

}
