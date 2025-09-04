package com.jacop.test.driven.design.post.repo;

import com.jacop.test.driven.design.post.data.Post;
import org.springframework.data.repository.ListCrudRepository;

public interface PostRepository extends ListCrudRepository<Post, Integer> {

}
