package com.example.socialmediaapi.service;

import com.example.socialmediaapi.dto.FileDto;
import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.model.File;
import com.example.socialmediaapi.model.Post;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.repositoty.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final FileService fileService;

    public Optional<Post> findPostId(int id) {
        var postFind = postRepository.findById(id);
        return postFind;
    }

    @Transactional(readOnly = true)
    public Optional<Post> findAllWitrUserAndFiles(int id) {
        final Optional<Post> posts = postRepository.findById(id);
        return posts;
    }

    public List<Post> findAll(int user_id, Pageable pageable) {
        Optional<User> user = userService.findById(user_id);
        System.out.println("findAll");
        var rsl = postRepository.findAllByUser(user.get(), pageable);
        for (Post post:rsl) {
            System.out.println("post: "+post);
        }
        return  rsl;
    }

    public Post create(PostDto postDto, List<FileDto> fileDtoList) {
        Post postNew = Post.builder().mess(postDto.getMess()).date(new Date()).user(userService.findById(postDto.getUser_id()).get()).build();
        Post postSave = postRepository.save(postNew);
        ArrayList<File> files = new ArrayList<>();
        for (FileDto fileDto : fileDtoList) {
            File c = fileService.save(fileDto, postSave);
            files.add(c);
        }
        return postSave;
    }

    public Optional<Post> update(PostDto postDto, List<FileDto> fileDtoList) {
        Optional<Post> postFindOptional = postRepository.findById(postDto.getId());
        if (postFindOptional.isEmpty()) {
            System.out.println("not found the post with id: " + postDto.getId());
            return postFindOptional;
        }
        Post postFind = postFindOptional.get();
        postFind.setMess(postDto.getMess());
        postRepository.save(postFind);
        ArrayList<File> files = new ArrayList<>();
        for (FileDto fileDto : fileDtoList) {
            File file = fileService.save(fileDto, postFind);
            files.add(file);
        }
        return Optional.of(postFind);
    }

    public boolean delete(int post_id) {
        var rsl = postRepository.findById(post_id);
        if (rsl.isEmpty()){
            return false;
        }
        postRepository.deleteById(post_id);
        return true;
    }
}
