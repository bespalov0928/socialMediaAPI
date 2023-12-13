package com.example.socialmediaapi.controller;

import com.example.socialmediaapi.dto.SubscriberDto;
import com.example.socialmediaapi.dto.UserDto;
import com.example.socialmediaapi.model.Subscriber;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.service.SubscriberService;
import com.example.socialmediaapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/subscriber")
@AllArgsConstructor
public class SubscriberController {
    SubscriberService subscriberService;
    UserService userService;

    @GetMapping("/user")
    public ResponseEntity<List<Subscriber>> findSubscriberByUser(@RequestBody UserDto userDto){
        List<Subscriber> rsl = subscriberService.findSubscriberByUser(userDto);
        return ResponseEntity.ok(rsl);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(@RequestBody SubscriberDto subscriberDto){
        Boolean rsl = subscriberService.subscribe(subscriberDto);
        return new ResponseEntity<>(rsl ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<User> unsubscribe(@RequestBody SubscriberDto subscriberDto){
        Boolean rsl = subscriberService.unsubscribe(subscriberDto);
        return new ResponseEntity<>(rsl ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }


}
