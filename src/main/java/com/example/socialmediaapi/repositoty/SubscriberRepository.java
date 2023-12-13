package com.example.socialmediaapi.repositoty;

import com.example.socialmediaapi.model.Subscriber;
import com.example.socialmediaapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {

    public List<Subscriber> findSubscriberByUser(@Param("user") User user);

    @Query("select s from Subscriber s where s.user.id =:user_id and s.subscriber.id = :subscriber_id")
    Optional<Subscriber> findByUserAndSubscriber(@PathVariable("user_id") Integer user_id, @PathVariable("subscriber_id") Integer subscriber_id);
}
