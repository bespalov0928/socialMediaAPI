package com.example.socialmediaapi.service;

import com.example.socialmediaapi.dto.FileDto;
import com.example.socialmediaapi.model.File;
import com.example.socialmediaapi.model.Post;
import com.example.socialmediaapi.repositoty.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;

    @Value("${file.directory}")
    private String storageDirectory;

    public FileService(FileRepository fileRepository, @Value("${file.directory}") String storageDirectory) {
        this.fileRepository = fileRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public File save(FileDto fileDto, Post post) {
        File file = null;
        var path = getNewFilePath(fileDto.getName());
        writeFileBytes(path, fileDto.getContent());
        var fileFind = fileRepository.findByPathAndPost(path, post);
        if (fileFind.isEmpty()) {
            file = new File(fileDto.getName(), path, post);
            System.out.println("new File");
        } else {
            file = fileFind.get();
            System.out.println("current File");
        }
        var rsl = fileRepository.save(file);
        return rsl;

    }

    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + sourceName;
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<FileDto> getFileById(int id) {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        byte[] content = readFileAsBytes(fileOptional.get().getPath());
        Optional<FileDto> rsl = Optional.of(new FileDto(fileOptional.get().getName(), content));
        return rsl;
    }

    public Optional<FileDto> getFileByName(String fileName) {
        Optional<File> rsl = fileRepository.findByName(fileName);
        if (rsl.isEmpty()) {
            return Optional.empty();
        }
        byte[] content = readFileAsBytes(storageDirectory + java.io.File.separator + rsl.get().getName());
        return Optional.of(FileDto.builder().name(rsl.get().getName()).content(content).build());
    }

    public Optional<FileDto> getFileByPathByPost(String filePath, Post post) {
        Optional<File> rsl = fileRepository.findByPathAndPost(filePath, post);
        if (rsl.isEmpty()) {
            return Optional.empty();
        }
        byte[] content = readFileAsBytes(storageDirectory + java.io.File.separator + rsl.get().getName());
        return Optional.of(FileDto.builder().name(rsl.get().getName()).content(content).build());
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(int id) {
        var fileOptional = fileRepository.findById(id);
        if (fileOptional.isPresent()) {
            deleteFile(fileOptional.get().getPath());
            fileRepository.deleteById(id);
        }
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
