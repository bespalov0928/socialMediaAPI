package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.InviteDto;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.service.UserService;
import com.example.socialmediaapi.swagger.SwaggerUserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Tag(name = "User", description = "The User API")
@SecurityRequirement(name = "JWT")
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController implements SwaggerUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> findAllUser() {
        var rsl = userService.findAll();
        if (rsl.isEmpty()) {
            throw new IllegalArgumentException("User list is empty");
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rsl);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        Optional<User> userFind = userService.findById(id);
        if (userFind.isEmpty()) {
            throw new IllegalArgumentException("User is not found.");
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userFind.get());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        var rsl = userService.findByEmail(email);
        if (rsl.isEmpty()) {
            throw new IllegalArgumentException("User is not found by email: " + email);
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rsl.get());
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User rsl = null;
        try {
            rsl = userService.createUser(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error save user");
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rsl);
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User is not found by email: " + email);
        }
        var rsl = userService.updateUser(email, user);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rsl.get());
    }

    @PostMapping("/friend")
    public ResponseEntity<User> addFriend(@Valid @RequestBody InviteDto inviteDto) {
        //отправка приглашения в друзья
        Optional<User> rslFriend = userService.addFriend(inviteDto);
        //добавление в качестве подписчика
        Optional<User> rslSub = userService.addSubscriber(inviteDto);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rslSub.get());
    }

    @PutMapping("/friend")
    public ResponseEntity<Boolean> appruvFriend(@Valid @RequestBody InviteDto inviteDto) {
        System.out.println();
        //подтверждение приглашения в друзья
        Boolean rsl = userService.appruvFriend(inviteDto);
        //добавление в качестве подписчика
        Optional<User> rslSub = userService.addSubscriber(InviteDto.builder().emailFriend(inviteDto.getEmailUser()).emailUser(inviteDto.getEmailFriend()).build());
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rsl);
    }

    @DeleteMapping("/friend")
    public ResponseEntity<Boolean> delFriend(@Valid @RequestBody InviteDto inviteDto) {
         //удаление из друзей
        Boolean rsl = userService.delFriend(inviteDto);
        Boolean rsl1 = userService.delFriend(InviteDto.builder().emailFriend(inviteDto.getEmailUser()).emailUser(inviteDto.getEmailFriend()).build());
        //отписка
        Optional<User> rslSub = userService.delSubscriber(inviteDto);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rsl);
    }

    @PostMapping("/subscriber")
    public ResponseEntity<User> addSubscriber(@Valid @RequestBody InviteDto inviteDto) {
         //добавление в качестве подписчика
        Optional<User> rslSub = userService.addSubscriber(inviteDto);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rslSub.get());
    }

    @DeleteMapping("/subscriber")
    public ResponseEntity<User> delSubscriber(@Valid @RequestBody InviteDto inviteDto) {
         Optional<User> rslSub = userService.delSubscriber(inviteDto);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rslSub.get());
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
