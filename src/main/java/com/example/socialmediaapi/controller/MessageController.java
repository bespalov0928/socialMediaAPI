package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.MessageDto;
import com.example.socialmediaapi.model.Message;
import com.example.socialmediaapi.service.MessageService;
import com.example.socialmediaapi.swagger.SwaggerMessageController;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Tag(name = "Message", description = "The Post API")
@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("api/v1/message")
@AllArgsConstructor
public class MessageController implements SwaggerMessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private final ObjectMapper objectMapper;
    MessageService messageService;

    @GetMapping("/{email}")
    public ResponseEntity<List<Message>> getAllMessageByUser(@PathVariable String email) {
        if (email == null || email == "") {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        var rsl = messageService.getAllMessageByUser(email);
        if (rsl.isEmpty()) {
            throw new IllegalArgumentException("Messages is not found, by user: " + email);
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rsl);
    }

    @PostMapping("/")
    public ResponseEntity<Boolean> addMessage(@RequestBody MessageDto messageDto) {
        var rsl = messageService.addMessage(messageDto);
        if (rsl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.OK, "User was not found by email");
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(true);
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
