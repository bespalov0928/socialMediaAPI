package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.SocialMediaApiApplication;
import com.example.socialmediaapi.dto.InviteDto;
import com.example.socialmediaapi.dto.UserDto;
import com.example.socialmediaapi.model.Invite;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.repositoty.InviteRepository;
import com.example.socialmediaapi.repositoty.SubscriberRepository;
import com.example.socialmediaapi.repositoty.UserRepository;
import com.example.socialmediaapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@Sql(scripts = "/sql/test_users.sql")
class UserControllerTest {
    @Mock
    UserRepository repository;

    @Mock
    InviteRepository inviteRepository;

    @Mock
    SubscriberRepository subscriberRepository;

    @Mock
    UserService userService;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    UserController controller;

    //@Disabled
    @Test
    @DisplayName("GET api/v1/user/ возвращает HTTP-ответ со статусом 200 OK и списком пользователей")
    void findAllUser() {
        //given
        var users = List.of(
                new User("user_test@mail.ru", "user_test"),
                new User("admin_test@mail.ru", "admin_test")
        );
        doReturn(users).when(this.repository).findAll();

        //when
        UserService userService = new UserService(repository, inviteRepository, subscriberRepository);
        UserController userController = new UserController(userService);
        var responseEntity = userController.findAllUser();
//        var x = this.controller.findAllUser();
//        this.repository.findAll();

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(users.toString(), responseEntity.getBody().toString());
    }

    //@Disabled
    @Test
    void findById() {
        //given
        var user = new User("user_test@mail.ru", "user_test");
        doReturn(Optional.of(user)).when(this.repository).findByIdWithFriends(1);

        //when
        UserService userService = new UserService(repository, inviteRepository, subscriberRepository);
        UserController userController = new UserController(userService);
        var responseEntity = userController.findById(1);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(user.toString(), responseEntity.getBody().toString());
    }

    //@Disabled
    @Test
    void findByEmail() {
        //given
        var user = new User("user_test@mail.ru", "user_test");
        doReturn(Optional.of(user)).when(this.repository).findByEmail("user_test@mail.ru");

        //when
        UserService userService = new UserService(repository, inviteRepository, subscriberRepository);
        UserController userController = new UserController(userService);
        var responseEntity = userController.findByEmail("user_test@mail.ru");

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(user.toString(), responseEntity.getBody().toString());
    }

    //@Disabled
    @Test
    void createUser() {
        //given
        var user = new User("client_test@mail.ru", "client_test");
        doReturn(user).when(this.repository).save(user);

        //when
        UserService userService = new UserService(repository, inviteRepository, subscriberRepository);
        UserController userController = new UserController(userService);
        var responseEntity = userController.createUser(user);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(user.toString(), responseEntity.getBody().toString());
    }

    //@Disabled
    @Test
    void updateUser() {
        //given
        var userNew = new User("client_test1@mail.ru", "client_test1");
        var userTest = new User("user_test@mail.ru", "user_test");

        var users = List.of(
                userTest,
                new User("admin_test@mail.ru", "admin_test")
        );
        doReturn(Optional.of(userTest)).when(this.repository).findByEmail("user_test@mail.ru");
        doReturn(userNew).when(this.repository).save(userNew);

        //when
        UserService userService = new UserService(repository, inviteRepository, subscriberRepository);
        UserController userController = new UserController(userService);
        var responseEntity = userController.updateUser("user_test@mail.ru", userNew);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(userNew.toString(), responseEntity.getBody().toString());
    }

    //@Disabled
    @Test
    void addFriend() {
        //given
        List<User> userList = new ArrayList<>();

        InviteDto inviteDto = new InviteDto("user_test@mail.ru", "admin_test@mail.ru", false);
        User user = new User(1, "user_test@mail.ru", "user_test", userList);
        User userFriend = new User(2, "admin_test@mail.ru", "admin_test");
        user.getFriends().add(userFriend);

        Invite invite = new Invite(user, userFriend, false);
        doReturn(Optional.of(user)).when(this.repository).findByEmail("user_test@mail.ru");
        doReturn(Optional.of(userFriend)).when(this.repository).findByEmail("admin_test@mail.ru");
        doReturn(Optional.of(invite)).when(this.inviteRepository).findByUserAndFriend(1, 2);

        //when
        UserService userService = new UserService(repository, inviteRepository, subscriberRepository);
        UserController userController = new UserController(userService);
        var responseEntity = userController.addFriend(inviteDto);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(user.toString(), responseEntity.getBody().toString());
    }

    //@Disabled
    @Test
    void appruvFriend() {
        //given
        InviteDto inviteDto = new InviteDto("user_test@mail.ru", "admin_test@mail.ru", true);
        User user = new User(1, "user_test@mail.ru", "user_test");
        User userFriend = new User(2, "admin_test@mail.ru", "admin_test");

        Invite invite = new Invite(user, userFriend, true);
        doReturn(Optional.of(user)).when(this.repository).findByEmail("user_test@mail.ru");
        doReturn(Optional.of(userFriend)).when(this.repository).findByEmail("admin_test@mail.ru");
        doReturn(Optional.of(invite)).when(this.inviteRepository).findByUserAndFriend(1, 2);

        //when
        UserService userService = new UserService(repository, inviteRepository, subscriberRepository);
        UserController userController = new UserController(userService);
        var responseEntity = userController.appruvFriend(inviteDto);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertTrue(responseEntity.getBody());

    }

    //@Disabled
    @Test
    void delFriend() {
        //given
        List<User> userList = new ArrayList<>();
        InviteDto inviteDto = new InviteDto("user_test@mail.ru", "admin_test@mail.ru", false);
        User user = new User(1, "user_test@mail.ru", "user_test", userList);
        User userFriend = new User(2, "admin_test@mail.ru", "admin_test");
        user.getFriends().add(userFriend);

        Invite invite = new Invite(user, userFriend, true);
        doReturn(Optional.of(user)).when(this.repository).findByEmail("user_test@mail.ru");
        doReturn(Optional.of(userFriend)).when(this.repository).findByEmail("admin_test@mail.ru");
        doReturn(Optional.of(invite)).when(this.inviteRepository).findByUserAndFriend(1, 2);

        //when
        UserService userService = new UserService(repository, inviteRepository, subscriberRepository);
        UserController userController = new UserController(userService);
        var responseEntity = userController.delFriend(inviteDto);

        //then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertTrue(responseEntity.getBody());

    }

}