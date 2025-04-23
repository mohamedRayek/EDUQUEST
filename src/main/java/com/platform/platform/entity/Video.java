package com.platform.platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Integer id;
    @Column(name = "video_url")
    private String url;
    @Column(name = "video_title")
    private String title;
    private double duration=0.0;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;


}
