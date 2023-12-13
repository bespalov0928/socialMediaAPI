package com.example.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "invites")
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private User user;

    @OneToOne
    private User friend;

    @Column(name = "appruvFriend")
    private Boolean appruvFriend;

    public Invite(User userFind, User user) {

    }

    public Invite() {

    }
}
