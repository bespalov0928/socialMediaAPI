package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.MessageDto;
import com.example.socialmediaapi.dto.UserDto;
import com.example.socialmediaapi.model.Message;
import com.example.socialmediaapi.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/message")
@AllArgsConstructor
public class MessageController {
    MessageService messageService;

    @GetMapping("/")
    public ResponseEntity<List<Message>> getAllMessageByUser(@RequestBody UserDto userDto) {
        var rsl = messageService.getAllMessageByUser(userDto);
        return ResponseEntity.ok(rsl);
    }

    @PostMapping("/")
    public ResponseEntity<Message> addMessage(@RequestBody MessageDto messageDto) {
        var rsl = messageService.addMessage(messageDto);
        return ResponseEntity.ok(rsl.get());
    }
}
