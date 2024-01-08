package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.SubscriberDto;
import com.example.socialmediaapi.model.Subscriber;
import com.example.socialmediaapi.service.SubscriberService;
import com.example.socialmediaapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Tag(name = "Subscriber", description = "The subscriber API")
@RestController
@RequestMapping("api/v1/subscriber")
@AllArgsConstructor
public class SubscriberController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class.getSimpleName());
    private final ObjectMapper objectMapper;
    SubscriberService subscriberService;
    UserService userService;

    @Operation(summary = "Search by subscriber User")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found subscriber by User successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Subscriber.class)))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Subscriber is not found by user email",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    })
    })
    @GetMapping("/{email}")
    public ResponseEntity<List<Subscriber>> findSubscriberByUser(@PathVariable String email) {
        List<Subscriber> rsl = subscriberService.findSubscriberByUser(email);
        if (rsl.isEmpty()) {
            throw new IllegalArgumentException("Subscribers is not found.");
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rsl);
    }

    @Operation(summary = "Create a subscribe")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Create a subscribe successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User and Subscriber email cannot be empty",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    })
    })
    @PostMapping("/")
    public ResponseEntity<Boolean> subscribe(@RequestBody SubscriberDto subscriberDto) {
        if (subscriberDto.getEmailUser() == null) {
            throw new IllegalArgumentException("User email cannot be empty");
        }
        if (subscriberDto.getEmailSubscriber() == null) {
            throw new IllegalArgumentException("Subscriber email cannot be empty");
        }
        Boolean rsl = subscriberService.subscribe(subscriberDto);
//        return new ResponseEntity<>(rsl ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rsl);

    }

    @Operation(summary = "Сancel subscription")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Сancel subscription successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User and Subscriber email cannot be empty",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    }
            )
    })
    @PutMapping("/")
    public ResponseEntity<Boolean> unsubscribe(@RequestBody SubscriberDto subscriberDto) {
        if (subscriberDto.getEmailUser() == null) {
            throw new IllegalArgumentException("User email cannot be empty");
        }
        if (subscriberDto.getEmailSubscriber() == null) {
            throw new IllegalArgumentException("Subscriber email cannot be empty");
        }
        Boolean rsl = subscriberService.unsubscribe(subscriberDto);
//        return new ResponseEntity<>(rsl ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rsl);
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
