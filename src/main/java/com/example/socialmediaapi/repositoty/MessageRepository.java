package com.example.socialmediaapi.repositoty;

import com.example.socialmediaapi.model.Message;
import com.example.socialmediaapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByUser(User user);
}
