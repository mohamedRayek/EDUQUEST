package com.platform.platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Integer id;
    @Column(name = "file_name")
    private String name;
    @Column(name = "file_url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


}
