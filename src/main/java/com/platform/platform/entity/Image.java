package com.platform.platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer id;

    @Column(name = "image_url")
    private String url;

    @Column(name = "image_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
