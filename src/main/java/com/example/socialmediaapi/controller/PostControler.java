package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.FileDto;
import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.model.Post;
import com.example.socialmediaapi.service.PostService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/post")
@AllArgsConstructor
public class PostControler {

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
        return rsl;

    }

    @PostMapping("/")
    public ResponseEntity<?> Save(@RequestPart("post") PostDto post, @RequestPart("file") MultipartFile file) throws IOException {
        try {
            Post postNew = postService.create(post, List.of(new FileDto(file.getOriginalFilename(), file.getBytes())));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.OK, "Error save Post");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestPart("post") PostDto post, @RequestPart("file") MultipartFile file) throws IOException {
        Optional<Post> postUpdate = postService.update(post, List.of(new FileDto(file.getOriginalFilename(), file.getBytes())));
        if (postUpdate.isEmpty()) {
            System.out.println("not found the post with id: " + postUpdate.get().getMess());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @DeleteMapping("/")
    public void delete(@RequestParam int id) {
        postService.delete(id);
    }

}
