package com.platform.platform.repo;

import com.platform.platform.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Optional<Rating> findByCourseIdAndStudentId(Integer courseId, Integer studentId);
}