package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.FileDto;
import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.model.Post;
import com.example.socialmediaapi.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Tag(name = "Post", description = "The Post API")
@RestController
@RequestMapping("api/v1/post")
@AllArgsConstructor
public class PostControler {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private final ObjectMapper objectMapper;
    private final PostService postService;

    @Operation(summary = "Search by post ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found post by ID successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Post.class)))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Post was not found by id",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    })
    })
    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable int id) {
        Optional<Post> postFind = postService.findAllWitrUserAndFiles(id);
        if (postFind.isEmpty()) {
            throw new IllegalArgumentException("Post was not found by id");
        }
        return ResponseEntity.ok(postFind.get());
    }

    @Operation(summary = "Search by post User")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found post by User successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Post.class)))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "User was not found by id",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    })
    })
    @GetMapping("/{user_id}")
    public ResponseEntity<List<Post>> findAll(@PathVariable int user_id,
                                              @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                              @RequestParam(value = "limit", defaultValue = "1000") @Min(1) @Max(1000) Integer limit) {
        var rsl = postService.findAll(user_id, PageRequest.of(offset, limit, Sort.by("date").descending()));
        if (rsl.isEmpty()) {
            throw new IllegalArgumentException("Users is not found by id: " + user_id);
        }
        return ResponseEntity.ok(rsl);

    }

    @Operation(summary = "Create post")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Create a post successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Post.class)))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error save Post",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    })
    })
    @PostMapping("/")
    public ResponseEntity<Post> createPost(@RequestPart("post") PostDto post, @RequestPart("file") MultipartFile file) throws IOException {
        Post postNew = null;
        try {
            postNew = postService.create(post, List.of(new FileDto(file.getOriginalFilename(), file.getBytes())));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error save Post");
        }
        return ResponseEntity.ok(postNew);
    }

    @Operation(summary = "Update post")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Create a post successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Post.class)))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "User is not found by email",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Post.class))
                    })
    })
    @PutMapping("/")
    public ResponseEntity<Post> update(@RequestPart("post") PostDto post, @RequestPart("file") MultipartFile file) throws IOException {
        Optional<Post> postUpdate = postService.update(post, List.of(new FileDto(file.getOriginalFilename(), file.getBytes())));
        if (postUpdate.isEmpty()) {
            throw new IllegalArgumentException("Not found the post with id: " + postUpdate.get().getMess());
        } else {
            return ResponseEntity.ok(postUpdate.get());
        }
    }

    @Operation(summary = "Delete post")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete post successful",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Post was not found",
                    content = {
                            @Content(
                                    mediaType = "application/json")
                    })
    })
    @DeleteMapping("/")
    public ResponseEntity delete(@RequestParam int id) {
        var rsl = postService.delete(id);
        if (!rsl) {
            throw new IllegalArgumentException("Post was not found");
        }
        return ResponseEntity.ok().build();
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
