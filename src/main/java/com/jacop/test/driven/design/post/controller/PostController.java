package com.jacop.test.driven.design.post.controller;

import com.jacop.test.driven.design.post.data.Post;
import com.jacop.test.driven.design.post.exceptions.PostNotFoundException;
import com.jacop.test.driven.design.post.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

    @GetMapping
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Post> findById(@PathVariable int id) {
        return Optional.ofNullable(postRepository.findById(id).orElseThrow(PostNotFoundException::new));
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<Post> create(@RequestBody @Validated Post post) {
        return Optional.ofNullable(postRepository.save(post));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Post update(@RequestBody @Validated Post post,@PathVariable Integer id) {
        Post postEntity = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        Post updated = new Post(
                postEntity.id(),
                postEntity.userId(),
                post.title(),
                post.body(),
                postEntity.version()

        );
        return postRepository.save(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        postRepository.deleteById(id);
    }
}
