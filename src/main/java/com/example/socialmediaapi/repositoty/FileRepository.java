package com.example.socialmediaapi.repositoty;

import com.example.socialmediaapi.dto.FileDto;
import com.example.socialmediaapi.model.File;
import com.example.socialmediaapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Integer> {

    public Optional<File> findByName(String fileName);

    public Optional<File> findByPathAndPost(String filePath, Post post);

}
