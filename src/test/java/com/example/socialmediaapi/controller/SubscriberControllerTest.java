package com.example.socialmediaapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubscriberControllerTest {

//    @Mock
//    SubscriberService subscriberService;

//    @InjectMocks
//    SubscriberController subscriberController;

    @Test
    void findSubscriberByUser() {
//        //given
//        var user = User.builder().id(1).firstname("user_test").email("user_test@mail.ru").build();
//        Message message = Message.builder().id(1).mess("mes").user(user).build();
//        List<Message> messageList = List.of(message);
//        doReturn(messageList).when(this.subscriberService).findSubscriberByUser(user.getEmail());
//
//        //when
//        var responseEntity = this.subscriberController.findSubscriberByUser(user.getEmail());
//
//        //then
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
//        assertEquals(messageList.toString(), responseEntity.getBody().toString());
    }

    @Test
    void subscribe() {
//        //given
//        var user = User.builder().id(1).firstname("user_test").email("user_test@mail.ru").build();
//        var subscriber = User.builder().id(1).firstname("subscriber_test").email("subscriber_test@mail.ru").build();
//        Message message = Message.builder().id(1).mess("mes").user(user).build();
//        List<Message> messageList = List.of(message);
//        SubscriberDto subscriberDto = SubscriberDto.builder().emailUser(user.getEmail()).emailSubscriber(subscriber.getEmail()).active(true).build();
//        doReturn(true).when(this.subscriberService).subscribe(subscriberDto);
//
//        //when
//        var responseEntity = this.subscriberController.subscribe(subscriberDto);
//
//        //then
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
//        assertTrue(responseEntity.getBody());
     }

    @Test
    void unsubscribe() {
//        //given
//        var user = User.builder().id(1).firstname("user_test").email("user_test@mail.ru").build();
//        var subscriber = User.builder().id(1).firstname("subscriber_test").email("subscriber_test@mail.ru").build();
//        Message message = Message.builder().id(1).mess("mes").user(user).build();
//        List<Message> messageList = List.of(message);
//        SubscriberDto subscriberDto = SubscriberDto.builder().emailUser(user.getEmail()).emailSubscriber(subscriber.getEmail()).active(false).build();
//        doReturn(true).when(this.subscriberService).unsubscribe(subscriberDto);
//
//        //when
//        var responseEntity = this.subscriberController.unsubscribe(subscriberDto);
//
//        //then
//        assertNotNull(responseEntity);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
//        assertTrue(responseEntity.getBody());

    }
}