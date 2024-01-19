package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.MessageDto;
import com.example.socialmediaapi.model.Message;
import com.example.socialmediaapi.service.MessageService;
import com.example.socialmediaapi.swagger.SwaggerMessageController;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "Message", description = "The Post API")
@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("api/v1/message")
@AllArgsConstructor
public class MessageController implements SwaggerMessageController {
    private final ObjectMapper objectMapper;
    private final MessageService messageService;

    @GetMapping("/{email}")
    public ResponseEntity<List<Message>> getAllMessageByUser(@PathVariable @Email  String email) {
        List<Message> rsl = messageService.getAllMessageByUser(email);
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
}
