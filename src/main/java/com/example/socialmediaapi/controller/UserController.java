package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.AuthenticationUser;
import com.example.socialmediaapi.dto.InviteDto;
import com.example.socialmediaapi.dto.SubscriberDto;
import com.example.socialmediaapi.dto.UserDto;
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
            throw new ResponseStatusException(HttpStatus.OK, "Users is not found.");
        }
        return ResponseEntity.ok(rsl);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        Optional<User> userFind = userService.findById(id);
        return ResponseEntity.ok(userFind.get());
    }

    @PutMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User rsl = userService.createUser(user);
        return ResponseEntity.ok(rsl);
    }

    @PostMapping("/")
    public ResponseEntity<User> findByEmail(@RequestBody AuthenticationUser userDto) {
        var rsl = userService.findByEmail(userDto);
        return ResponseEntity.ok(rsl.get());
    }

    @PostMapping("/addFriend")
    public ResponseEntity<User> addFriend(@RequestBody InviteDto inviteDto){
        Optional<User> rsl = userService.addFriend(inviteDto);
        return ResponseEntity.ok(rsl.get());
    }

    @PostMapping("/delFriend")
    public ResponseEntity<User> delFriend(@RequestBody InviteDto inviteDto){
        Boolean rsl = userService.delFriend(inviteDto);
        return new ResponseEntity<>(rsl ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/appruvFriend")
    public ResponseEntity<Void> appruvFriend(@RequestBody InviteDto inviteDto){
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
