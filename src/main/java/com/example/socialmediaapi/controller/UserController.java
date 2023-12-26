package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.InviteDto;
import com.example.socialmediaapi.dto.UserDto;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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

@Tag(name = "User", description = "The User API")
@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private final ObjectMapper objectMapper;
    UserService userService;


    @Operation(summary = "Gets all users")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "User list is empty",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    })
    })
    @GetMapping("/")
    public ResponseEntity<List<User>> findAllUser() {
        var rsl = userService.findAll();
        if (rsl.isEmpty()) {
            throw new IllegalArgumentException("User list is empty");
        }
        return ResponseEntity.ok(rsl);
    }

    @Operation(summary = "Search by user ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found user by ID successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "User is not found by ID",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    })
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable int id) {
        Optional<User> userFind = userService.findById(id);
        if (userFind.isEmpty()) {
            throw new IllegalArgumentException("User is not found.");
        }
        return ResponseEntity.ok(userFind.get());
    }

    @Operation(summary = "Search by user Email")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found user by Email successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "User is not found by email",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    })
    })
    @GetMapping("/{email}")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        var rsl = userService.findByEmail(email);
        if (rsl.isEmpty()) {
            throw new IllegalArgumentException("User is not found by email: " + email);
        }
        return ResponseEntity.ok(rsl.get());
    }

    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Create user successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "user email and password cannot be empty",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    })
    })
    @PostMapping("/")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) throws ResponseStatusException {
        if (userDto.getEmail() == null) {
            throw new IllegalArgumentException("user email cannot be empty");
        }
        if (userDto.getPassword() == null) {
            throw new IllegalArgumentException("User password cannot be empty");
        }
        try {
            User rsl = userService.createUser(userDto);
        } catch (Exception e){
            throw new IllegalArgumentException("Error save user");
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Update user successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "User is not found by email",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    })
    })
    @PutMapping("/{email}")
    public ResponseEntity<Void> updateUser(@PathVariable String email, @RequestBody UserDto userDto) throws ResponseStatusException {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User is not found by email: " + email);
        }
        var rsl = userService.updateUser(email, userDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Request for friendship")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Request for friendship successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "user and friend email cannot be empty",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    })
    })
    @PostMapping("/friend")
    public ResponseEntity<User> addFriend(@Valid @RequestBody InviteDto inviteDto) {
        if (inviteDto.getEmailUser() == null) {
            throw new IllegalArgumentException("user email cannot be empty");
        }
        if (inviteDto.getEmailFriend() == null) {
            throw new IllegalArgumentException("friend email cannot be empty");
        }
        Optional<User> rsl = userService.addFriend(inviteDto);
        return ResponseEntity.ok(rsl.get());
    }

    @Operation(summary = "Friendship request approval")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Friendship request approval successful",
                    content = {
                            @Content(mediaType = "application/json")
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "user and friend email cannot be empty",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    })
    })
    @PutMapping("/friend")
    public ResponseEntity<Void> appruvFriend(@Valid @RequestBody InviteDto inviteDto) {
        if (inviteDto.getEmailUser() == null) {
            throw new IllegalArgumentException("user email cannot be empty");
        }
        if (inviteDto.getEmailFriend() == null) {
            throw new IllegalArgumentException("friend email cannot be empty");
        }
        System.out.println();
        Boolean rsl = userService.appruvFriend(inviteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Request for removal from friends")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Request for removal from friends successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "user and friend email cannot be empty",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    })
    })
    @DeleteMapping("/friend")
    public ResponseEntity<HttpStatus> delFriend(@Valid @RequestBody InviteDto inviteDto) {
        if (inviteDto.getEmailUser() == null) {
            throw new IllegalArgumentException("user email cannot be empty");
        }
        if (inviteDto.getEmailFriend() == null) {
            throw new IllegalArgumentException("friend email cannot be empty");
        }
        Boolean rsl = userService.delFriend(inviteDto);
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
