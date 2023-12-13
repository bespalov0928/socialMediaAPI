package com.example.socialmediaapi.model;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Subscribers")
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToOne
    private User user;

    @OneToOne
    private User subscriber;

    @Column(name = "active")
    private Boolean active;

}
