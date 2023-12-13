package com.example.socialmediaapi.service;

import com.example.socialmediaapi.dto.AuthenticationUser;
import com.example.socialmediaapi.dto.InviteDto;
import com.example.socialmediaapi.model.Invite;
import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.repositoty.InviteRepository;
import com.example.socialmediaapi.repositoty.SubscriberRepository;
import com.example.socialmediaapi.repositoty.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    UserRepository userRepository;
    InviteRepository inviteRepository;
    SubscriberRepository subscriberRepository;

    public List<User> findAll() {
        var rsl = userRepository.findAll();
        return rsl;
    }

    public Optional<User> findById(int id) {
        var rsl = userRepository.findByIdWithFriends(id);
        return rsl;
    }

    public Optional<User> findByEmail(AuthenticationUser authenticationUser) {
        var rsl = userRepository.findByEmail(authenticationUser.getEmail());
        return rsl;
    }

    public User createUser(User user) {
        var rsl = userRepository.save(user);
        return rsl;
    }

    public Optional<User> addFriend(InviteDto inviteDto) {
        Optional<User> optionalUser = userRepository.findByEmail(inviteDto.getUser().getEmail());
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User userFind = optionalUser.get();
        Optional<User> optionalFriendFind = userRepository.findByEmail(inviteDto.getFriend().getEmail());
        User friendFind = optionalFriendFind.get();

        List<User> listFriends = userFind.getFriends();
        if (!listFriends.contains(friendFind)) {
            listFriends.add(friendFind);
            userRepository.save(userFind);
        }

        Optional<Invite> optionalInvite = inviteRepository.findByUserAndFriend(userFind.getId(), friendFind.getId());
        if (optionalInvite.isEmpty()) {
            Invite invite = new Invite();
            invite.setUser(userFind);
            invite.setFriend(friendFind);
            invite.setAppruvFriend(inviteDto.getAppruvFriend());
            inviteRepository.save(invite);

            //Если отправил заявку значит стал подписчиком
            SubscriberService subscriberService = new SubscriberService(subscriberRepository, userRepository);
            subscriberService.ConnectSubcriber(userFind, friendFind);
            boolean rsl = subscriberService.addSubscriber(userFind, friendFind, true);
        }

        return Optional.of(userFind);
    }

    public Boolean delFriend(InviteDto inviteDto) {
        Optional<User> optionalUser = userRepository.findByEmail(inviteDto.getUser().getEmail());
        if (optionalUser.isEmpty()) {
            return false;
        }
        User userFind = optionalUser.get();
        User friendFind = userRepository.findByEmail(inviteDto.getFriend().getEmail()).get();

        Optional<Invite> optionalInvite = inviteRepository.findByUserAndFriend(userFind.getId(), friendFind.getId());
        if (optionalInvite.isEmpty()) {
            return false;
        }
        Invite invite = optionalInvite.get();
        if (invite.getAppruvFriend()) {
            invite.setAppruvFriend(inviteDto.getAppruvFriend());
            inviteRepository.save(invite);
            //если удаляется из друзей, значит отписывается
            SubscriberService subscriberService = new SubscriberService(subscriberRepository, userRepository);
            subscriberService.updateSubscriber(userFind, friendFind, false);
        }
        return true;
    }

    public Boolean appruvFriend(InviteDto inviteDto) {
        Optional<User> optionalUser = userRepository.findByEmail(inviteDto.getUser().getEmail());
        if (optionalUser.isEmpty()) {
            return false;
        }
        User userFind = optionalUser.get();
        User friendFind = userRepository.findByEmail(inviteDto.getFriend().getEmail()).get();

        Optional<Invite> optionalInvite = inviteRepository.findByUserAndFriend(userFind.getId(), friendFind.getId());
        if (optionalInvite.isEmpty()) {
            return false;
        }
        Invite invite = optionalInvite.get();
        if (!invite.getAppruvFriend()) {
            invite.setAppruvFriend(inviteDto.getAppruvFriend());
            inviteRepository.save(invite);
            //Если друзья то значит подписчики друг на друга
            SubscriberService subscriberService = new SubscriberService(subscriberRepository, userRepository);
            subscriberService.ConnectSubcriber(userFind, friendFind);
            subscriberService.addSubscriber(userFind, friendFind, true);
            subscriberService.addSubscriber(friendFind, userFind, true);
        }
        return true;
    }

}
