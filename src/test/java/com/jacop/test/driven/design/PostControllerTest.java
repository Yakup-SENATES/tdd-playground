package com.jacop.test.driven.design;

import com.google.gson.Gson;
import com.jacop.test.driven.design.post.data.Post;
import com.jacop.test.driven.design.post.controller.PostController;
import com.jacop.test.driven.design.post.exceptions.PostNotFoundException;
import com.jacop.test.driven.design.post.repo.PostRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest kullanmıyoruz neden ?
@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    PostRepository postRepository;

    Gson gson;

    List<Post> posts = new ArrayList<>();

    @BeforeEach
    void setUp() {
        // create some posts
        posts = List.of(

                new Post(1, 1, "Hello, World!", "This is my first post.", null),
                new Post(2, 1, "Second Post!", "This is my second post.", null)
        );
        gson = new Gson();
    }

    // rest api


    //list
    @Test
    void shouldFindAllPosts() throws Exception {
        // given
        String jsonResponse = """
                    [ {
                        "id":1,
                        "userId":1,
                        "title":"Hello, World!",
                        "body":"This is my first post.",
                        "version": null
                        },
                        {
                         "id":2,
                         "userId":1,
                         "title":"Second Post!",
                         "body":"This is my second post.",
                         "version": null
                        }
                    ]
                """;

        when(postRepository.findAll()).thenReturn(posts);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

    }

    // api/posts/1
    @Test
    void shouldFindPostWhenGivenValidId() throws Exception {
        // given - 1
        Post firstPost = posts.get(0);
        when(postRepository.findById(1))
                .thenReturn(Optional.of(firstPost));

        JSONObject json = new JSONObject();
        json.put("id", firstPost.id());
        json.put("userId", firstPost.userId());
        json.put("title", firstPost.title());
        json.put("body", firstPost.body());
        json.put("version", JSONObject.NULL);

        String jsonString = json.toString();


        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }

    // api/post/999
    @Test
    void shouldNotFindPostWhenGivenInvalidId() throws Exception {
        when(postRepository.findById(999))
                .thenThrow(PostNotFoundException.class);

        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isNotFound());
    }

    // create
    @Test
    void shouldCreateNewPostWhenPostIsValid() throws Exception {
        var post = new Post(3, 1, "NEW_TITLE", "NEW BODY", null);
        when(postRepository.save(post)).thenReturn(post);
        // Gson could be used here instead of JSONObject, but I kept it as a JSONObject for demonstration purposes.
        JSONObject json = new JSONObject();
        json.put("id", post.id());
        json.put("userId", post.userId());
        json.put("title", post.title());
        json.put("body", post.body());
        json.put("version", JSONObject.NULL);

        String jsonString = json.toString();


        mockMvc.perform(
                        post("/api/posts/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonString)
                )
                .andExpect(status().isCreated());
    }

    // not create
    @Test
    void shouldNotCreatePostWhenGivenPostIsNotValid() throws Exception {
        var post = new Post(3, 1, "", "", null);
        when(postRepository.save(post)).thenReturn(post);


        mockMvc.perform(
                post("/api/posts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(post))
        ).andExpect(status().isBadRequest());
    }

    //update
    @Test
    void updateWhenGivenPostIsValid() throws Exception {
        var updated = new Post(1, 1, "this is a NEW_TITLE", "NEW BODY", 1);
        when(postRepository.findById(1)).thenReturn(Optional.of(updated));
        when(postRepository.save(updated)).thenReturn(updated);

        mockMvc.perform(
                put("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(updated))
        ).andExpect(status().isAccepted());

    }
    //not-update
    @Test
    void notUpdateWhenGivenPostIsNotValid() throws Exception {
        var updated = new Post(1, 1, "", "", 1);
        when(postRepository.findById(1)).thenReturn(Optional.of(updated));
        when(postRepository.save(updated)).thenReturn(updated);

        mockMvc.perform(
                put("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(updated))
        ).andExpect(status().isBadRequest());

    }
    //not-update id is not valid
    @Test
    void notUpdateWhenGivenIdIsNotValid() throws Exception {
        var updated = new Post(999, 1, "", "", 1);
        when(postRepository.findById(999)).thenThrow(PostNotFoundException.class);

        mockMvc.perform(
                put("/api/posts/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(updated))
        ).andExpect(status().isBadRequest());

    }

    // delete
    @Test
    void deleteWhenGivenIdHasAMatch() throws Exception {

        doNothing().when(postRepository).deleteById(1);

        mockMvc.perform(
                delete("/api/posts/1")
        ).andExpect(status().isNoContent());

        verify(postRepository, times(1)).deleteById(1);
    }

}
