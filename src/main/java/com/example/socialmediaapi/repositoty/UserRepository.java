package com.example.socialmediaapi.repositoty;

import com.example.socialmediaapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u left join fetch u.friends where u.id=:id")
    public Optional<User> findByIdWithFriends(@PathVariable("id") Integer id);

    @Query("select u from User u left join fetch u.subscribersUser where u.email=:email")
    public Optional<User> findByEmailWithSubscriber(@PathVariable("email") String email);

    public Optional<User> findByEmail(String Email);
}
