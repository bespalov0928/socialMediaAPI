package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.FileDto;
import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.model.Post;
import com.example.socialmediaapi.service.PostService;
import com.example.socialmediaapi.swagger.SwaggerPostController;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Tag(name = "Post", description = "The Post API")
@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("api/v1/post")
@AllArgsConstructor
public class PostControler implements SwaggerPostController {
    private final ObjectMapper objectMapper;
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable int id) {
        Optional<Post> postFind = postService.findAllWitrUserAndFiles(id);
        if (postFind.isEmpty()) {
            throw new IllegalArgumentException("Post was not found by id");
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(postFind.get());
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<Post>> findAllUser(@PathVariable int user_id,
                                              @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                              @RequestParam(value = "limit", defaultValue = "1000") @Min(1) @Max(1000) Integer limit) {
        var rsl = postService.findAll(user_id, PageRequest.of(offset, limit, Sort.by("date").descending()));
        if (rsl.isEmpty()) {
            throw new IllegalArgumentException("Users is not found by id: " + user_id);
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rsl);

    }

    @PostMapping("/")
    public ResponseEntity<Post> createPost(@Valid  @RequestPart("post") PostDto post, @Valid  @RequestPart("file") MultipartFile file) {
        Post postNew = null;
        try {
            postNew = postService.create(post, List.of(new FileDto(file.getOriginalFilename(), file.getBytes())));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error save Post");
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(postNew);
    }

    @PutMapping("/")
    public ResponseEntity<Post> update(@RequestPart("post") PostDto post, @RequestPart("file") MultipartFile file) throws IOException {
        Optional<Post> postUpdate = postService.update(post, List.of(new FileDto(file.getOriginalFilename(), file.getBytes())));
        if (postUpdate.isEmpty()) {
            throw new IllegalArgumentException("Not found the post with id: " + postUpdate.get().getMess());
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(postUpdate.get());
    }

    @DeleteMapping("/")
    public ResponseEntity delete(@RequestParam int id) {
        var rsl = postService.delete(id);
        if (!rsl) {
            throw new IllegalArgumentException("Post was not found");
        }
        return ResponseEntity.ok().build();
    }
}
