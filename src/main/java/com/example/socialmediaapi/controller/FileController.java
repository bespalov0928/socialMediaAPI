package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.FileDto;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.service.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

@Tag(name = "File", description = "The file API")
@RestController
@RequestMapping("api/v1/file")
@Data
@AllArgsConstructor
public class FileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private final ObjectMapper objectMapper;
    private FileService fileService;

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
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<FileDto> contentOptional = fileService.getFileById(id);
        if (contentOptional.isEmpty()) {
            throw new IllegalArgumentException("File not found by id: "+id);
//            return ResponseEntity.notFound().build();
        }
        byte[] rsl = contentOptional.get().getContent();
        return ResponseEntity.ok(rsl);
    }

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
    @GetMapping("/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        Optional<FileDto> fileDto = fileService.getFileByName(name);
        if (fileDto.isEmpty()) {
            throw new IllegalArgumentException("File not found by name:" + name);
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .header(name)
//                    .contentType(MediaType.APPLICATION_JSON).build();
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .contentLength(fileDto.get().getContent().length)
                .body(fileDto.get().getContent());
    }

    @Operation(summary = "Create a file")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Create a file successful"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error save File")

    })
    @PostMapping("/")
    public ResponseEntity<HttpStatus> create(@RequestPart("file") MultipartFile file) throws IOException {
        FileDto fileDto = new FileDto(file.getOriginalFilename(), file.getBytes());
        try {
            var file_ = fileService.save(fileDto, null);
        }catch (Exception e) {
            throw new IllegalArgumentException("Error save file: "+file.getName());
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
