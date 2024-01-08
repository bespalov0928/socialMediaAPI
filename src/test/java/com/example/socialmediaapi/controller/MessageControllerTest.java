package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.MessageDto;
import com.example.socialmediaapi.dto.UserDto;
import com.example.socialmediaapi.model.Message;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @Mock
    MessageService messageService;

    @InjectMocks
    MessageController messageController;

    @Test
    void getAllMessageByUser() {
        //given
        var user = User.builder().id(1).firstname("user_test").email("user_test@mail.ru").build();
        Message message = Message.builder().id(1).mess("mes").user(user).build();
        List<Message> messageList = List.of(message);
        doReturn(messageList).when(this.messageService).getAllMessageByUser(user.getEmail());

        //when
        var responseEntity = this.messageController.getAllMessageByUser(user.getEmail());

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(messageList.toString(), responseEntity.getBody().toString());
    }

    @Test
    void addMessage() {
        //given
        var user = User.builder().id(1).firstname("user_test").email("user_test@mail.ru").build();
        Message message = Message.builder().id(1).mess("mes").user(user).build();
        List<Message> messageList = List.of(message);
        MessageDto messageDto = MessageDto.builder().mess("mes").user(UserDto.builder().email("user_test@mail.ru").password("user_test").build()).build();
        doReturn(Optional.of(message)).when(this.messageService).addMessage(messageDto);

        //when
        var responseEntity = this.messageController.addMessage(messageDto);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertTrue(responseEntity.getBody());
    }
}