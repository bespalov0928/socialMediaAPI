package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.InviteDto;
import com.example.socialmediaapi.model.Invite;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.repositoty.InviteRepository;
import com.example.socialmediaapi.repositoty.SubscriberRepository;
import com.example.socialmediaapi.repositoty.UserRepository;
import com.example.socialmediaapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

//@DataJpaTest
@ExtendWith(MockitoExtension.class)
//@Sql(scripts = "/sql/test_users.sql")
class UserControllerTest {
//    @Mock
//    UserRepository repository;
//
//    @Mock
//    InviteRepository inviteRepository;
//
//    @Mock
//    SubscriberRepository subscriberRepository;
//
//    @Mock
//    ObjectMapper objectMapper;

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Test
    @DisplayName("GET api/v1/user/ возвращает HTTP-ответ со статусом 200 OK и списком пользователей")
    void findAllUser() {
        //given
        var users = List.of(
                User.builder().id(1).firstname("user_test").email("user_test@mail.ru").build(),
                User.builder().id(1).firstname("admin_test").email("admin_test@mail.ru").build()
        );
        doReturn(users).when(this.userService).findAll();

        //when
        var responseEntity = this.userController.findAllUser();

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(users.toString(), responseEntity.getBody().toString());
    }

    @Test
    void findById() {
        //given
        var user = User.builder().id(1).firstname("user_test").email("user_test@mail.ru").build();
        doReturn(Optional.of(user)).when(this.userService).findById(user.getId());

        //when
        var responseEntity = this.userController.findById(1);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(user.toString(), responseEntity.getBody().toString());
    }

    @Test
    void findByEmail() {
        //given
        var user = User.builder().id(1).firstname("user_test").email("user_test@mail.ru").build();
        doReturn(Optional.of(user)).when(this.userService).findByEmail(user.getEmail());

        //when
        var responseEntity = userController.findByEmail(user.getEmail());

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(user.toString(), responseEntity.getBody().toString());
    }

    @Test
    void createUser() {
        //given
        var user = User.builder().id(1).firstname("client_test").email("client_test@mail.ru").password("client_test").build();
        doReturn(user).when(this.userService).createUser(user);

        //when
        var responseEntity = this.userController.createUser(user);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(user.toString(), responseEntity.getBody().toString());
    }

    @Test
    void updateUser() {
        //given
        var userNew = User.builder().id(1).firstname("client_test1").email("client_test1@mail.ru").password("client_test1").build();
        var userTest = User.builder().id(2).firstname("user_test").email("user_test@mail.ru").password("user_test").build();
        doReturn(Optional.of(userTest)).when(this.userService).findByEmail(userTest.getEmail());
        doReturn(Optional.of(userNew)).when(this.userService).updateUser(userTest.getEmail(), userNew);

        //when
        var responseEntity = this.userController.updateUser(userTest.getEmail(), userNew);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(userNew.toString(), responseEntity.getBody().toString());
    }

    @Test
    void addFriend() {
        //given
        InviteDto inviteDto = InviteDto.builder().emailUser("user_test@mail.ru").emailFriend("admin_test@mail.ru").appruvFriend(false).build();
        User user = User.builder().id(1).firstname("user_test").email("user_test@mail.ru").password("user_test").friends(new ArrayList<User>()).build();
        User userFriend = User.builder().id(2).firstname("admin_test").email("admin_test@mail.ru").password("admin_test").build();
        user.getFriends().add(userFriend);
        doReturn(Optional.of(user)).when(this.userService).addFriend(inviteDto);

        //when
        var responseEntity = this.userController.addFriend(inviteDto);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(user.toString(), responseEntity.getBody().toString());
    }

    @Test
    void appruvFriend() {
        //given
        InviteDto inviteDto = InviteDto.builder().emailUser("user_test@mail.ru").emailFriend("admin_test@mail.ru").appruvFriend(true).build();
        doReturn(true).when(this.userService).appruvFriend(inviteDto);

        //when
        var responseEntity = this.userController.appruvFriend(inviteDto);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertTrue(responseEntity.getBody());
    }

    @Test
    void delFriend() {
        //given
        InviteDto inviteDto = InviteDto.builder().emailUser("user_test@mail.ru").emailFriend("admin_test@mail.ru").appruvFriend(false).build();
        doReturn(true).when(this.userService).delFriend(inviteDto);

        //when
        var responseEntity = userController.delFriend(inviteDto);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertTrue(responseEntity.getBody());
    }

}