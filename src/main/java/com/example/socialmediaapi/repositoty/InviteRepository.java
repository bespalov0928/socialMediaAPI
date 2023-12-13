package com.example.socialmediaapi.repositoty;

import com.example.socialmediaapi.model.Invite;
import com.example.socialmediaapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Integer> {

    @Query("select i from Invite i where i.user.id = :user_id and i.friend.id = :friend_id")
    Optional<Invite> findByUserAndFriend(@PathVariable("user_id") Integer user_id, @PathVariable("friend_id") Integer friend_id);
}
