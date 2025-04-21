package com.platform.platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class QuestionBank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_bank_id")
    private Integer id;

    @Column(name = "subject")
    private String subject;

    @OneToMany(mappedBy = "questionBank",
            cascade = CascadeType.ALL)
    private List<Question> questions;


@OneToMany(mappedBy = "questionBank",cascade = CascadeType.ALL)
@JsonIgnore
private List<Question>quiz;




}
