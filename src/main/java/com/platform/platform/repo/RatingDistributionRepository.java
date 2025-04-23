package com.platform.platform.repo;

import com.platform.platform.entity.RatingDistribution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingDistributionRepository extends JpaRepository<RatingDistribution, Integer> {
    Optional<RatingDistribution> findByCourseId(Integer courseId);
}