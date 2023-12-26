package com.example.socialmediaapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "date")
    private Date date;

    @NotBlank(message = "Message must be not empty")
    @Column(name = "mess")
    String mess;

    @NotBlank(message = "User must be not empty")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
}
