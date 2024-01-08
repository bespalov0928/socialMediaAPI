package com.example.socialmediaapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "invites")
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "User must be not empty")
    @OneToOne
    private User user;

    @NotBlank(message = "Friend must be not empty")
    @OneToOne
    private User friend;

    @NotNull(message = "Appruv must be not empty")
    @Column(name = "appruvFriend")
    private Boolean appruvFriend;

    public Invite(User user, User userFind, boolean appruvFriend) {
        this.user = user;
        this.friend = userFind;
        this.appruvFriend = appruvFriend;
    }

//    public Invite() {
//
//    }
}
