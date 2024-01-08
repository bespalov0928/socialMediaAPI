package com.example.socialmediaapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Setter
@Getter
@Entity
//@NoArgsConstructor
@Table(name = "posts")
public class Post {

    public Post() {
    }

    public Post(int id, Date date, String mess, User user, List<File> files) {
        this.id = id;
        this.date = date;
        this.mess = mess;
        this.user = user;
        this.files = files;
    }

    public Post(int id, String mess, User user) {
        this.id = id;
        this.mess = mess;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date")
    private Date date;

    @NotBlank(message = "Message must be not empty")
    @Column(name = "mess")
    private String mess;

    @NotBlank(message = "User must be not empty")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    @JsonManagedReference
    private List<File> files;

    @Override
    public String toString() {
        return "Post{" +
                "date=" + date +
                ", mess='" + mess + '\'' +
                ", user=" + user +
                '}';
    }
}
