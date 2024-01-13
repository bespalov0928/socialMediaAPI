package com.example.socialmediaapi.swagger;

import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.model.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SwaggerPostController {
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
    ResponseEntity<Post> findById(int id);

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
    ResponseEntity<List<Post>> findAllUser(int user_id, Integer offset, Integer limit);

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
    ResponseEntity<Post> createPost(PostDto post,MultipartFile file);

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
    ResponseEntity<Post> update(PostDto post, MultipartFile file) throws IOException;

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
    ResponseEntity delete(int id);


}
