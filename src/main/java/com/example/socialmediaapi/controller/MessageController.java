package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.MessageDto;
import com.example.socialmediaapi.dto.UserDto;
import com.example.socialmediaapi.model.Message;
import com.example.socialmediaapi.service.MessageService;
import com.example.socialmediaapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/message")
@AllArgsConstructor
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private final ObjectMapper objectMapper;
    MessageService messageService;

    @GetMapping("/")
    public ResponseEntity<List<Message>> getAllMessageByUser(@RequestBody UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail() == "") {
            throw new IllegalArgumentException("email cannot be empty");
        }
        var rsl = messageService.getAllMessageByUser(userDto);
        if (rsl.isEmpty()) {
            throw new IllegalArgumentException("Messages is not found, bu user: "+ userDto.getEmail());
        }

        return ResponseEntity.ok(rsl);
    }

    @PostMapping("/")
    public ResponseEntity<Message> addMessage(@RequestBody MessageDto messageDto) {
        if (messageDto.getUser().getEmail() == null || messageDto.getUser().getEmail() == "") {
            throw new ResponseStatusException(HttpStatus.OK, "email cannot be empty");
        }
        var rsl = messageService.addMessage(messageDto);
        return ResponseEntity.ok(rsl.get());
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {{
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }

}
