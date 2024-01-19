package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.FileDto;
import com.example.socialmediaapi.service.FileService;
import com.example.socialmediaapi.swagger.SwaggerFileController;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Tag(name = "File", description = "The file API")
@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("api/v1/file")
@AllArgsConstructor
public class FileController implements SwaggerFileController {
    private final ObjectMapper objectMapper;
    private final FileService fileService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<FileDto> contentOptional = fileService.getFileById(id);
        if (contentOptional.isEmpty()) {
            throw new IllegalArgumentException("File not found by id: " + id);
        }
        return ResponseEntity.ok(contentOptional.get().getContent());
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        Optional<FileDto> fileDto = fileService.getFileByName(name);
        if (fileDto.isEmpty()) {
            throw new IllegalArgumentException("File not found by name:" + name);
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .contentLength(fileDto.get().getContent().length)
                .body(fileDto.get().getContent());
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> create(@RequestPart("file") MultipartFile file) throws IOException {
        FileDto fileDto = new FileDto(file.getOriginalFilename(), file.getBytes());
        try {
            var file_ = fileService.save(fileDto, null);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error save file: " + file.getName());
        }
        return ResponseEntity.ok().build();
    }

}
