package io.mhan.springjpatest.config.init;

import io.mhan.springjpatest.posts.entity.Post;
import io.mhan.springjpatest.posts.repository.PostRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;

@Configuration
public class InitData {

    @Bean
    @Profile("default") // default 모드에서만 실행
    public ApplicationRunner defaultInit(PostRepository postRepository) {
        return args -> {

            for (int i=1; i<=15; i++) {
                Post post = Post.builder()
                        .title("title" + i)
                        .content("content" + i)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
                postRepository.save(post);
            }

        };
    }
}
