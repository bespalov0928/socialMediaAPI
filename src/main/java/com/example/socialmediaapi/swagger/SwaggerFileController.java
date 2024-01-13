package com.example.socialmediaapi.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SwaggerFileController {
    @Operation(summary = "Search by file ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found file by ID successful",
                    content = {
                            @Content(
                                    mediaType = "application/pdf",
                                    schema = @Schema(implementation = byte[].class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "File not found by id")

    })
    ResponseEntity<?> getById(int id);

    @Operation(summary = "Search by file name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found file by name successful",
                    content = {
                            @Content(
                                    mediaType = "application/pdf",
                                    schema = @Schema(implementation = byte[].class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "File not found by name")

    })
    ResponseEntity<?> getByName(String name);

    @Operation(summary = "Create a file")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Create a file successful"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error save File")

    })
    ResponseEntity<HttpStatus> create(MultipartFile file) throws IOException;

}
