package com.example.socialmediaapi.service;

import com.example.socialmediaapi.dto.SubscriberDto;
import com.example.socialmediaapi.dto.UserDto;
import com.example.socialmediaapi.model.Subscriber;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.repositoty.SubscriberRepository;
import com.example.socialmediaapi.repositoty.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubscriberService {
    SubscriberRepository subscriberRepository;
    UserRepository userRepository;

    public List<Subscriber> findSubscriberByUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            return new ArrayList<>();
        }
        User user = optionalUser.get();
        List<Subscriber> rsl = subscriberRepository.findSubscriberByUser(user);
        return rsl;
    }

    public Boolean subscribe(SubscriberDto subscriberDto) {
        Optional<User> optionalUser = userRepository.findByEmail(subscriberDto.getEmailUser());
        if (optionalUser.isEmpty()) {
            return false;
        }
        User userFind = optionalUser.get();
        Optional<User> optionalSubscriber = userRepository.findByEmail(subscriberDto.getEmailSubscriber());
        if (optionalSubscriber.isEmpty()) {
            return false;
        }
        User subscriberFind = optionalSubscriber.get();

        ConnectSubcriber(userFind, subscriberFind);
        boolean rsl = addSubscriber(userFind, subscriberFind, subscriberDto.getActive());
        if (!rsl){
            updateSubscriber(userFind, subscriberFind, subscriberDto.getActive());
        }
        return true;
    }

    public Boolean unsubscribe(SubscriberDto subscriberDto) {
//        Optional<User> optionalUser = userRepository.findByEmail(subscriberDto.getEmailUser());
//        if (optionalUser.isEmpty()) {
//            return false;
//        }
//        User userFind = optionalUser.get();
//
//        Optional<User> optionalSubscriber = userRepository.findByEmail(subscriberDto.getEmailSubscriber());
//        if (optionalSubscriber.isEmpty()) {
//            return false;
//        }
//        User subscriberFind = optionalSubscriber.get();
//
//        List<User> listSubscribers = userFind.getSubscribersUser();
//        if (!listSubscribers.contains(subscriberFind)) {
//            listSubscribers.add(subscriberFind);
//            userRepository.save(userFind);
//        }
//        Optional<Subscriber> optionalSubscriberFind = subscriberRepository.findByUserAndSubscriber(userFind.getId(), subscriberFind.getId());
//        if (optionalSubscriberFind.isEmpty()) {
//            return false;
//        }
//
//        updateSubscriber(userFind, subscriberFind, subscriberDto.getActive());
        return true;
    }

    public boolean addSubscriber(User user, User subscriber, Boolean active) {
        Optional<Subscriber> optionalSubscriberFind = subscriberRepository.findByUserAndSubscriber(user.getId(), subscriber.getId());
        if (optionalSubscriberFind.isEmpty()) {
            Subscriber NewSubscriber = new Subscriber();
            NewSubscriber.setUser(user);
            NewSubscriber.setSubscriber(subscriber);
            NewSubscriber.setActive(active);
            subscriberRepository.save(NewSubscriber);
            return true;
        }
        return false;
    }

    public boolean updateSubscriber(User user, User subscriber, Boolean active) {
        Optional<Subscriber> optionalSubscriberFind = subscriberRepository.findByUserAndSubscriber(user.getId(), subscriber.getId());
        if (!optionalSubscriberFind.isEmpty() & optionalSubscriberFind.get().getActive() != active){
            Subscriber subscriberFind = optionalSubscriberFind.get();
            subscriberFind.setActive(active);
            subscriberRepository.save(subscriberFind);
        }
        return true;
    }

    public boolean ConnectSubcriber(User user, User subscriber){
//        List<User> listSubscribers = user.getSubscribersUser();
//        if (!listSubscribers.contains(subscriber)) {
//            listSubscribers.add(subscriber);
//            userRepository.save(user);
//        }
        return true;
    }

}
