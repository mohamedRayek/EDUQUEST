package com.platform.platform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teacher implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "subject")
    private String subject;
    @Column(name = "bio")
    private String bio;
    @Column(name = "rate")
    private float rate=0.0f;
    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "teacher",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    //@JsonIgnore
    private List<Course> courses;

    @OneToMany(mappedBy = "teacher",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Quiz> quizzes;
}
