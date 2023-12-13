package com.example.socialmediaapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date")
    private Date date;
    @Column(name = "mess")
    private String mess;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    @JsonManagedReference
    private List<File> files;


}
