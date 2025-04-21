package com.platform.platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "time")
    private Time time;
    @Column(name = "subject")
    private String subject;



    @OneToMany(mappedBy = "quiz",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany
    @JoinTable(name = "quiz_student",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;


    @ManyToOne
    @JoinColumn(name = "question_bank_id")
    private QuestionBank questionBank;


    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinTable(name = "quiz_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id"))
    //@JsonIgnore
    private List<Course> course;

}
