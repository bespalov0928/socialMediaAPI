package com.example.socialmediaapi.service;

import com.example.socialmediaapi.dto.AuthenticationUser;
import com.example.socialmediaapi.dto.InviteDto;
import com.example.socialmediaapi.dto.UserDto;
import com.example.socialmediaapi.model.Invite;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.repositoty.InviteRepository;
import com.example.socialmediaapi.repositoty.SubscriberRepository;
import com.example.socialmediaapi.repositoty.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

//@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
//@Setter
@Service
public class UserService {
    private final UserRepository userRepository;
    private final InviteRepository inviteRepository;
    private final SubscriberRepository subscriberRepository;

//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

//    public UserService(InviteRepository inviteRepository) {
//        this.inviteRepository = inviteRepository;
//    }

//    public UserService(SubscriberRepository subscriberRepository) {
//        this.subscriberRepository = subscriberRepository;
//    }

    public List<User> findAll() {
        var rsl = userRepository.findAll();
        return rsl;
    }

    public Optional<User> findById(int id) {
        var rsl = userRepository.findById(id);
//        var rsl = userRepository.findByIdWithFriends(id);
        return rsl;
    }

    public Optional<User> findByEmail(String email) {
        var rsl = userRepository.findByEmail(email);
        return rsl;
    }

    public User createUser(User user) {
//        User user = new User(userDto.getEmail(), userDto.getPassword());
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

//        List<User> listFriends = userFind.getFriends();
//        if (!listFriends.contains(friendFind)) {
//            listFriends.add(friendFind);
//            userRepository.save(userFind);
//        }

        Optional<Invite> optionalInvite = inviteRepository.findByUserAndFriend(userFind.getId(), friendFind.getId());
        if (optionalInvite.isEmpty()) {
            Invite invite = new Invite();
            invite.setUser(userFind);
            invite.setFriend(friendFind);
            invite.setAppruvFriend(inviteDto.getAppruvFriend());
            inviteRepository.save(invite);

            //Если отправил заявку значит стал подписчиком
//            SubscriberService subscriberService = new SubscriberService(subscriberRepository, userRepository);
//            subscriberService.ConnectSubcriber(userFind, friendFind);
//            boolean rsl = subscriberService.addSubscriber(userFind, friendFind, true);
        }
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

        Optional<Invite> optionalInvite = inviteRepository.findByUserAndFriend(userFind.getId(), friendFind.getId());
        if (optionalInvite.isEmpty()) {
            return false;
        }
        Invite invite = optionalInvite.get();
        if (!invite.getAppruvFriend()) {
            invite.setAppruvFriend(inviteDto.getAppruvFriend());
            inviteRepository.save(invite);
            //Если друзья то значит подписчики друг на друга
//            SubscriberService subscriberService = new SubscriberService(subscriberRepository, userRepository);
//            subscriberService.ConnectSubcriber(userFind, friendFind);
//            subscriberService.addSubscriber(userFind, friendFind, true);
//            subscriberService.addSubscriber(friendFind, userFind, true);
        }
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

        Optional<Invite> optionalInvite = inviteRepository.findByUserAndFriend(userFind.getId(), friendFind.getId());
        if (optionalInvite.isEmpty()) {
            return false;
        }
        Invite invite = optionalInvite.get();
        if (invite.getAppruvFriend()) {
            invite.setAppruvFriend(inviteDto.getAppruvFriend());
            inviteRepository.save(invite);
            //если удаляется из друзей, значит отписывается
//            SubscriberService subscriberService = new SubscriberService(subscriberRepository, userRepository);
//            subscriberService.updateSubscriber(userFind, friendFind, false);

        }
        return true;
    }
}
