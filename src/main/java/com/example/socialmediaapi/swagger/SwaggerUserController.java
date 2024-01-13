package com.example.socialmediaapi.swagger;

import com.example.socialmediaapi.dto.InviteDto;
import com.example.socialmediaapi.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SwaggerUserController {

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
    ResponseEntity<List<User>> findAllUser();

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
    ResponseEntity<User> findById(int id);

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
    ResponseEntity<User> findByEmail(String email);

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
    ResponseEntity<User> createUser(User user);

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
    ResponseEntity<User> updateUser(String email, User user);

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
    ResponseEntity<User> addFriend(InviteDto inviteDto);

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
    ResponseEntity<Boolean> appruvFriend(InviteDto inviteDto);

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
    ResponseEntity<Boolean> delFriend(InviteDto inviteDto);

    @Operation(summary = "Request for subscriber")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Request for subscriber successful",
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
    ResponseEntity<User> addSubscriber(InviteDto inviteDto);

    @Operation(summary = "Request for removal from subscriber")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Request for removal from subscriber successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "user and subscriber email cannot be empty",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HttpStatus.class))
                    })
    })
    ResponseEntity<User> delSubscriber(InviteDto inviteDto) ;

}
