package com.example.socialmediaapi.service;

import com.example.socialmediaapi.dto.InviteDto;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.repositoty.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        var rsl = userRepository.findAll();
        return rsl;
    }

    public Optional<User> findById(int id) {
        var rsl = userRepository.findByIdWithFriends(id);
        return rsl;
    }

    public Optional<User> findByEmail(String email) {
        var rsl = userRepository.findByEmail(email);
        return rsl;
    }

    public User createUser(User user) {
        var rsl = userRepository.save(user);
        return rsl;
    }

    public Optional<User> updateUser(String email, User user) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        User userFind = userOptional.get();
        userFind.setPassword(user.getPassword());
        userFind.setEmail(user.getEmail());
        var rsl = userRepository.save(user);
        return Optional.of(rsl);
    }

    public Optional<User> addFriend(InviteDto inviteDto) {
        Optional<User> optionalUser = userRepository.findByEmail(inviteDto.getEmailUser());
        Optional<User> optionalFriendFind = userRepository.findByEmail(inviteDto.getEmailFriend());
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        if (optionalFriendFind.isEmpty()) {
            return Optional.empty();
        }
        User userFind = optionalUser.get();
        User friendFind = optionalFriendFind.get();
        updateFriend(userFind, friendFind, true);
        return Optional.of(userFind);
    }

    public Boolean appruvFriend(InviteDto inviteDto) {
        Optional<User> optionalUser = userRepository.findByEmail(inviteDto.getEmailUser());
        Optional<User> optionalFriend = userRepository.findByEmail(inviteDto.getEmailFriend());
        if (optionalUser.isEmpty()) {
            return false;
        }
        if (optionalFriend.isEmpty()) {
            return false;
        }
        User userFind = optionalUser.get();
        User friendFind = optionalFriend.get();

        updateFriend(friendFind, userFind, true);
        return true;
    }

    public Boolean delFriend(InviteDto inviteDto) {
        Optional<User> optionalUser = userRepository.findByEmail(inviteDto.getEmailUser());
        Optional<User> optionalUserFriend = userRepository.findByEmail(inviteDto.getEmailFriend());
        if (optionalUser.isEmpty()) {
            return false;
        }
        if (optionalUserFriend.isEmpty()) {
            return false;
        }
        User userFind = optionalUser.get();
        User friendFind = optionalUserFriend.get();

        updateFriend(userFind, friendFind, false);
        updateFriend(friendFind, userFind, false);

        return true;
    }

    private void updateFriend(User user, User friend, Boolean add) {
        List<User> listFriends = user.getFriends();
        if (add && !listFriends.contains(friend)) {
            listFriends.add(friend);
        } else if (!add && listFriends.contains(friend)) {
            listFriends.remove(friend);
        }
        userRepository.save(user);
    }

    //подписчики
    public Optional<User> addSubscriber(InviteDto inviteDto) {
        Optional<User> optionalUser = userRepository.findByEmail(inviteDto.getEmailUser());
        Optional<User> optionalFriendFind = userRepository.findByEmail(inviteDto.getEmailFriend());
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        if (optionalFriendFind.isEmpty()) {
            return Optional.empty();
        }
        User userFind = optionalUser.get();
        User friendFind = optionalFriendFind.get();
        updateSubscriber(userFind, friendFind, true);
        return Optional.of(userFind);
    }

    public Optional<User> delSubscriber(InviteDto inviteDto) {
        Optional<User> optionalUser = userRepository.findByEmail(inviteDto.getEmailUser());
        Optional<User> optionalUserFriend = userRepository.findByEmail(inviteDto.getEmailFriend());
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        if (optionalUserFriend.isEmpty()) {
            return Optional.empty();
        }
        User userFind = optionalUser.get();
        User friendFind = optionalUserFriend.get();
        updateSubscriber(userFind, friendFind, false);
        return Optional.of(userFind);
    }

    private void updateSubscriber(User user, User friend, Boolean add) {
        List<User> listSubscribers = user.getSubscribersUser();
        if (add && !listSubscribers.contains(friend)) {
            listSubscribers.add(friend);
        } else if (!add && listSubscribers.contains(friend)) {
            listSubscribers.remove(friend);
        }
        userRepository.save(user);
    }
}
