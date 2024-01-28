package com.example.socialmediaapi.service;

import com.example.socialmediaapi.dto.MessageDto;
import com.example.socialmediaapi.model.Message;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.repositoty.MessageRepository;
import com.example.socialmediaapi.repositoty.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public List<Message> getAllMessageByUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return new ArrayList<Message>();
        }
        List<Message> messageList = messageRepository.findByUser(optionalUser.get());
        return messageList;
    }

    public Optional<Message> addMessage(MessageDto messageDto) {
        Optional<User> optionalUser = userRepository.findByEmail(messageDto.getUser().getEmail());
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        Message message = Message.builder().user(user).mess(messageDto.getMess()).build();
        var rsl = messageRepository.save(message);
        return Optional.of(rsl);
    }
}
