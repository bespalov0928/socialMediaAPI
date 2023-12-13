package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.SubscriberDto;
import com.example.socialmediaapi.dto.UserDto;
import com.example.socialmediaapi.model.Subscriber;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.service.SubscriberService;
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
import java.util.Optional;

@RestController
@RequestMapping("api/v1/subscriber")
@AllArgsConstructor
public class SubscriberController {
    private final ObjectMapper objectMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    SubscriberService subscriberService;
    UserService userService;

    @GetMapping("/user")
    public ResponseEntity<List<Subscriber>> findSubscriberByUser(@RequestBody UserDto userDto) {
        List<Subscriber> rsl = subscriberService.findSubscriberByUser(userDto);
        if (rsl.isEmpty()) {
            throw new IllegalArgumentException("Subscribers is not found.");
        }
        return ResponseEntity.ok(rsl);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(@RequestBody SubscriberDto subscriberDto) {
        if (subscriberDto.getUser().getEmail() == null) {
            throw new IllegalArgumentException("User email cannot be empty");
        }
        if (subscriberDto.getSubscriber().getEmail() == null) {
            throw new IllegalArgumentException("Subscriber email cannot be empty");
        }
        Boolean rsl = subscriberService.subscribe(subscriberDto);
        return new ResponseEntity<>(rsl ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<User> unsubscribe(@RequestBody SubscriberDto subscriberDto) {
        if (subscriberDto.getUser().getEmail() == null) {
            throw new IllegalArgumentException("User email cannot be empty");
        }
        if (subscriberDto.getSubscriber().getEmail() == null) {
            throw new IllegalArgumentException("Subscriber email cannot be empty");
        }
        Boolean rsl = subscriberService.unsubscribe(subscriberDto);
        return new ResponseEntity<>(rsl ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
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
