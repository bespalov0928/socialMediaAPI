package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.FileDto;
import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.model.Post;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.repositoty.PostRepository;
import com.example.socialmediaapi.service.FileService;
import com.example.socialmediaapi.service.PostService;
import com.example.socialmediaapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PostControlerTest {

    @Mock
    PostService postService;

    @InjectMocks
    PostControler postController;

    @Test
    void findById() {
        //given
        User user = User.builder().id(1).firstname("user").email("user@mail.ru").build();
        Post post = Post.builder().id(1).mess("mes").user(user).build();
        doReturn(Optional.of(post)).when(this.postService).findAllWitrUserAndFiles(1);

        //when
        ResponseEntity responseEntity = postController.findById(1);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(post.toString(), responseEntity.getBody().toString());
    }

    @Test
    void findAll() {
        //given
        List<Post> postList = new ArrayList<>();
        User user1 = User.builder().id(1).firstname("user").email("user@mail.ru").build();
        User user2 = User.builder().id(2).firstname("admin").email("admin@mail.ru").build();
        postList.add(Post.builder().id(1).mess("mes1").user(user1).build());
        postList.add(Post.builder().id(1).mess("mes2").user(user2).build());
        doReturn(postList).when(this.postService).findAll(1, PageRequest.of(0, 10, Sort.by("date").descending()));

        //when
        ResponseEntity responseEntity = this.postController.findAll(1, 0, 10);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(postList.toString(), responseEntity.getBody().toString());
    }

    @Test
    void createPost() throws IOException {
        //given
        User user = User.builder().id(1).firstname("user").email("user@mail.ru").build();
        Post post = Post.builder().id(1).mess("mes1").user(user).build();

        byte[] bytes = new byte[0];
        MultipartFile file = new MockMultipartFile("bytes", bytes);
        List<FileDto> list = List.of(new FileDto(file.getOriginalFilename(), file.getBytes()));
        PostDto postDto = PostDto.builder().id(1).mess("mes1").user_id(1).build();
        doReturn(post).when(this.postService).create(postDto, list);

        //when
        ResponseEntity responseEntity = postController.createPost(postDto, file);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(post.toString(), responseEntity.getBody().toString());
    }

    @Test
    void update() throws IOException {
        //given
        User user = User.builder().id(1).firstname("user").email("user@mail.ru").build();
        Post post = Post.builder().id(1).mess("mes").user(user).build();

        byte[] bytes = new byte[0];
        MultipartFile file = new MockMultipartFile("bytes", bytes);
        List<FileDto> list = List.of(new FileDto(file.getOriginalFilename(), file.getBytes()));
        PostDto postDto = PostDto.builder().id(1).mess("mes1").user_id(1).build();
        doReturn(Optional.of(post)).when(this.postService).update(postDto, list);

        //when
        ResponseEntity responseEntity = postController.update(postDto, file);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(post.toString(), responseEntity.getBody().toString());
    }

    @Test
    void delete() {
        //given
        User user = User.builder().id(1).firstname("user").email("user@mail.ru").build();
        Post post = Post.builder().id(1).mess("mes").user(user).build();
        doReturn(true).when(this.postService).delete(post.getId());

        //when
        ResponseEntity responseEntity = postController.delete(post.getId());

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}