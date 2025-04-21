package com.platform.platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer id;
    @Column(name = "question")
    private String question;
    @Column(name = "choice_a")
    private String option1;
    @Column(name = "choice_b")
    private String option2;
    @Column(name = "choice_c")
    private String option3;
    @Column(name = "choice_d")
    private String option4;
    @Column(name = "right_choice")
    private char rightChoice;
    @Column(name = "subject")
    private String subject;
    @Column(name = "lesson_name")
    private String lessonName;
    @Column(name = "course")
    private String course;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    @JsonIgnore
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "question_bank_id")
    @JsonIgnore
    private QuestionBank questionBank;


}
