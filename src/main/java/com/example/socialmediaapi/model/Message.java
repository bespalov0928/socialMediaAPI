package com.example.socialmediaapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "mess")
    String mess;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
}
