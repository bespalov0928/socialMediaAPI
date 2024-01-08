package com.example.socialmediaapi.repositoty;

import com.example.socialmediaapi.model.Post;
import com.example.socialmediaapi.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
//public interface PostRepository {
    public List<Post> findAllByUser(User user, Pageable pageable);

//    @Query("select p from Post p join fetch p.user join fetch p.files where p.id = :id")
    Optional<Post> findById(@Param("id") int id);
}
