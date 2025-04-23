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
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "subject")
    private String subject;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @Column(name = "discount")
    private float discount;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "lesson_number")
    private Integer lessonNumber;

    @Column(name = "level")
    private Integer level;

    @Column(name = "student_number")
    private Integer studentNumber;

    @Column(name = "rate")
    private float rate;

    @Column(name = "expiration_time")
    private Time expirationTime;

    @Column(name = "goals", length = 2000)
    @Convert(converter = StringListConverter.class)
    private List<String> goals;

    @Column(name = "requirements", length = 2000)
    @Convert(converter = StringListConverter.class)
    private List<String> requirements;

    @Column(name = "includes", length = 2000)
    @Convert(converter = StringListConverter.class)
    private List<String> includes;





    @ManyToOne
    @JoinColumn(name = "teacher_id")
    //@JsonIgnore
    private Teacher teacher;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinTable(name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    //@JsonIgnore
    private List<Student> students;


    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    //@JsonIgnore
    private List<Video> videos;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    //@JsonIgnore
    private List<Image> images;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    //@JsonIgnore
    private List<File> files;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    //@JsonIgnore
    private List<Schedule> schedule;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinTable(name = "quiz_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id"))
    //@JsonIgnore
    private List<Quiz> quizzes;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToOne(mappedBy = "course", cascade = CascadeType.ALL)
    private RatingDistribution ratingDistribution;

}
