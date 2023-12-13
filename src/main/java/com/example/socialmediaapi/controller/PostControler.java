package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.FileDto;
import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.model.Post;
import com.example.socialmediaapi.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/post")
@AllArgsConstructor
public class PostControler {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private final ObjectMapper objectMapper;
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable int id) {
        Optional<Post> postFind = postService.findAllWitrUserAndFiles(id);
        return ResponseEntity.ok(postFind.get());
    }

    @GetMapping("/user/{user_id}")
    public List<Post> findAll(@PathVariable int user_id,
                              @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                              @RequestParam(value = "limit", defaultValue = "1000") @Min(1) @Max(1000) Integer limit) {
        var rsl = postService.findAll(user_id, PageRequest.of(offset, limit, Sort.by("date").descending()));
        if (rsl.isEmpty()) {
            throw new IllegalArgumentException("Users is not found.");
        }
        return rsl;

    }

    @PostMapping("/")
    public ResponseEntity<?> createPost(@RequestPart("post") PostDto post, @RequestPart("file") MultipartFile file) throws IOException {
        try {
            Post postNew = postService.create(post, List.of(new FileDto(file.getOriginalFilename(), file.getBytes())));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error save Post");
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestPart("post") PostDto post, @RequestPart("file") MultipartFile file) throws IOException {
        Optional<Post> postUpdate = postService.update(post, List.of(new FileDto(file.getOriginalFilename(), file.getBytes())));
        if (postUpdate.isEmpty()) {
            throw new IllegalArgumentException("not found the post with id: " + postUpdate.get().getMess());
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/")
    public void delete(@RequestParam int id) {
        postService.delete(id);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {{
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }

}
