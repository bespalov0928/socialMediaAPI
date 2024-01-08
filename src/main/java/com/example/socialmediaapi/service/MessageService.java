package com.example.socialmediaapi.service;

import com.example.socialmediaapi.dto.MessageDto;
import com.example.socialmediaapi.dto.UserDto;
import com.example.socialmediaapi.model.Message;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.repositoty.MessageRepository;
import com.example.socialmediaapi.repositoty.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {
    MessageRepository messageRepository;
    UserRepository userRepository;
    public List<Message> getAllMessageByUser(String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            return new ArrayList<>();
        }
        List<Message> messageList = messageRepository.findByUser(optionalUser.get());
        return messageList;
    }

    public Optional<Message> addMessage(MessageDto messageDto) {
        Optional<User> optionalUser = userRepository.findByEmail(messageDto.getUser().getEmail());
        if (optionalUser.isEmpty()){
            return Optional.empty();
        }
        User user = optionalUser.get();
//        Message message = new Message();
//        message.setUser(user);
//        message.setMess(messageDto.getMess());
        Message message = Message.builder().user(user).mess(messageDto.getMess()).build();
        var rsl = messageRepository.save(message);
        return Optional.of(rsl);
    }
}
