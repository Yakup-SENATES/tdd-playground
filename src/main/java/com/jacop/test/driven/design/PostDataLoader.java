package com.jacop.test.driven.design;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacop.test.driven.design.post.data.Posts;
import com.jacop.test.driven.design.post.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class PostDataLoader implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final PostRepository postRepository;



    @Override
    public void run(String... args) {

        if (postRepository.count() == 0) {
            String POST_JSON = "/data/posts.json";
            log.info("Loading into database from json : {}", POST_JSON);
            try (InputStream inputStream = TypeReference.class.getResourceAsStream(POST_JSON)) {
                Posts response = objectMapper.readValue(inputStream, Posts.class);
                postRepository.saveAll(response.posts());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read Json data");
            }
        }

    }
}
