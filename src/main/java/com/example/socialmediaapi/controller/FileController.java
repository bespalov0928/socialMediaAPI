package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.FileDto;
import com.example.socialmediaapi.model.File;
import com.example.socialmediaapi.service.FileService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/file")
@Data
@AllArgsConstructor
public class FileController {
    private FileService fileService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<FileDto> contentOptional = fileService.getFileById(id);
        if (contentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        byte[] rsl = contentOptional.get().getContent();
        return ResponseEntity.ok(rsl);
    }

    @GetMapping("/")
    public ResponseEntity<?> getByName(@RequestParam String name) {
        Optional<FileDto> fileDto = fileService.getFileByName(name);
        if (fileDto.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .header(name)
                    .contentType(MediaType.APPLICATION_JSON).build();
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .contentLength(fileDto.get().getContent().length)
                .body(fileDto.get().getContent());
    }


    @PostMapping("/")
    public void create(@RequestPart("file") MultipartFile file) throws IOException {
        String fileName = file.getName();
        FileDto fileDto = new FileDto(file.getOriginalFilename(), file.getBytes());
        var file_ = fileService.save(fileDto, null);
    }

}
