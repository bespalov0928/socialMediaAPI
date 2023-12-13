package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.InviteDto;
import com.example.socialmediaapi.model.User;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    UserService userService;
    private final ObjectMapper objectMapper;

    @GetMapping("/")
    public ResponseEntity<List<User>> findAllUser() {
        var rsl = userService.findAll();
        if (rsl.isEmpty()) {
            throw new IllegalArgumentException("Posts is not found.");
        }
        return ResponseEntity.ok(rsl);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        Optional<User> userFind = userService.findById(id);
        if (userFind.isEmpty()) {
            throw new IllegalArgumentException("User is not found.");
        }
        return ResponseEntity.ok(userFind.get());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        var rsl = userService.findByEmail(email);
        if (rsl.isEmpty()) {
            throw new IllegalArgumentException("User is not found by email: " + email);
        }
        return ResponseEntity.ok(rsl.get());
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) throws ResponseStatusException {
        if (user.getEmail() == null || user.getEmail() == "") {
            throw new IllegalArgumentException("email cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword() == "") {
            throw new IllegalArgumentException("password cannot be empty");
        }
        User rsl = userService.createUser(user);
        return ResponseEntity.ok(rsl);
    }

    @PostMapping("/addFriend")
    public ResponseEntity<User> addFriend(@RequestBody InviteDto inviteDto) {
        if (inviteDto.getUser().getEmail() == null) {
            throw new IllegalArgumentException("user email cannot be empty");
        }
        if (inviteDto.getFriend().getEmail() == null) {
            throw new IllegalArgumentException("friend email cannot be empty");
        }
        Optional<User> rsl = userService.addFriend(inviteDto);
        return ResponseEntity.ok(rsl.get());
    }

    @DeleteMapping("/")
    public ResponseEntity<User> delFriend(@RequestBody InviteDto inviteDto) {
        if (inviteDto.getUser().getEmail() == null) {
            throw new IllegalArgumentException("user email cannot be empty");
        }
        if (inviteDto.getFriend().getEmail() == null) {
            throw new IllegalArgumentException("friend email cannot be empty");
        }
        Boolean rsl = userService.delFriend(inviteDto);
        return new ResponseEntity<>(rsl ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/appruvFriend")
    public ResponseEntity<Void> appruvFriend(@RequestBody InviteDto inviteDto) {
        if (inviteDto.getUser().getEmail() == null) {
            throw new IllegalArgumentException("user email cannot be empty");
        }
        if (inviteDto.getFriend().getEmail() == null) {
            throw new IllegalArgumentException("friend email cannot be empty");
        }
        System.out.println();
        Boolean rsl = userService.appruvFriend(inviteDto);
        return new ResponseEntity<>(HttpStatus.OK);
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
