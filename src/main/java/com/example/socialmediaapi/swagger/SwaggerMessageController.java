package com.example.socialmediaapi.swagger;

import com.example.socialmediaapi.dto.MessageDto;
import com.example.socialmediaapi.model.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SwaggerMessageController {
    @Operation(summary = "Search by message email")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found message by email successful",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Message.class)))
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Email cannot be empty; User was not found by email",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    })
    })
    ResponseEntity<List<Message>> getAllMessageByUser(String email);

    @Operation(summary = "Create a message")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Create a message successful",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Email cannot be empty; User was not found by email",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    })
    })

    ResponseEntity<Boolean> addMessage(MessageDto messageDto);

}
