package com.example.socialmediaapi.model;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "Subscribers")
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotBlank(message = "User must be not empty")
    @OneToOne
    private User user;

    @NotBlank(message = "Subscriber must be not empty")
    @OneToOne
    private User subscriber;

    @NotNull(message = "Active must be not empty")
    @Column(name = "active")
    private Boolean active;

}
