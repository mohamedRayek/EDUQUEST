package com.platform.platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rating_distribution")
public class RatingDistribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "one_stars", columnDefinition = "integer default 0")
    private Integer oneStars = 0;

    @Column(name = "two_stars", columnDefinition = "integer default 0")
    private Integer twoStars = 0;

    @Column(name = "three_stars", columnDefinition = "integer default 0")
    private Integer threeStars = 0;

    @Column(name = "four_stars", columnDefinition = "integer default 0")
    private Integer fourStars = 0;

    @Column(name = "five_stars", columnDefinition = "integer default 0")
    private Integer fiveStars = 0;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;
}