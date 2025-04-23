package com.platform.platform.Services;

import com.platform.platform.entity.*;
import com.platform.platform.repo.CourseRepository;
import com.platform.platform.repo.RatingDistributionRepository;
import com.platform.platform.repo.RatingRepository;
import com.platform.platform.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final CourseRepository courseRepository;
    private final RatingDistributionRepository ratingDistributionRepository;

    public RatingService(RatingRepository ratingRepository,
                         CourseRepository courseRepository,
                         RatingDistributionRepository ratingDistributionRepository) {
        this.ratingRepository = ratingRepository;
        this.courseRepository = courseRepository;
        this.ratingDistributionRepository = ratingDistributionRepository;
    }

    @Transactional
    public Rating addRating(Integer courseId, Integer studentId, Integer stars) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5 stars");
        }

        // Check if student already rated this course
        Optional<Rating> existingRating = ratingRepository.findByCourseIdAndStudentId(courseId, studentId);
        
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        
        RatingDistribution distribution = course.getRatingDistribution();
        if (distribution == null) {
            distribution = new RatingDistribution();
            distribution.setCourse(course);
            course.setRatingDistribution(distribution);
        }

        Rating rating;
        if (existingRating.isPresent()) {
            // Update existing rating
            rating = existingRating.get();
            updateDistribution(distribution, rating.getStars(), false); // Remove old rating
            rating.setStars(stars);
        } else {
            // Create new rating
            rating = new Rating();
            rating.setStars(stars);
            rating.setCourse(course);
            // You'll need to set the student here, likely by fetching it from studentRepository
        }

        updateDistribution(distribution, stars, true); // Add new rating
        ratingDistributionRepository.save(distribution);
        
        // Update course average rating
        updateCourseAverageRating(course, distribution);
        courseRepository.save(course);

        return ratingRepository.save(rating);
    }

    private void updateDistribution(RatingDistribution distribution, Integer stars, boolean isAdd) {
        int change = isAdd ? 1 : -1;
        
        switch (stars) {
            case 1:
                distribution.setOneStars(distribution.getOneStars() + change);
                break;
            case 2:
                distribution.setTwoStars(distribution.getTwoStars() + change);
                break;
            case 3:
                distribution.setThreeStars(distribution.getThreeStars() + change);
                break;
            case 4:
                distribution.setFourStars(distribution.getFourStars() + change);
                break;
            case 5:
                distribution.setFiveStars(distribution.getFiveStars() + change);
                break;
        }
    }

    private void updateCourseAverageRating(Course course, RatingDistribution distribution) {
        int totalRatings = distribution.getOneStars() + 
                          distribution.getTwoStars() + 
                          distribution.getThreeStars() + 
                          distribution.getFourStars() + 
                          distribution.getFiveStars();
        
        if (totalRatings > 0) {
            float sum = distribution.getOneStars() * 1 + 
                       distribution.getTwoStars() * 2 + 
                       distribution.getThreeStars() * 3 + 
                       distribution.getFourStars() * 4 + 
                       distribution.getFiveStars() * 5;
            course.setRate(sum / totalRatings);
        } else {
            course.setRate(0);
        }
    }

    public RatingDistribution getRatingDistribution(Integer courseId) {
        return ratingDistributionRepository.findByCourseId(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Rating distribution not found for course"));
    }
}