package com.platform.platform.entity.dto;

import com.platform.platform.entity.Course;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CourseDTOMapper implements Function<Course, CourseDTO> {
    @Override
    public CourseDTO apply(Course Course) {
        return new CourseDTO (
                Course.getId(),
                Course.getTitle(),
                Course.getSubTitle(),
                Course.getSubject(),
                Course.getDescription(),
                Course.getPrice(),
                Course.getDiscount(),
                Course.getDuration(),
                Course.getLessonNumber(),
                Course.getLevel(),
                Course.getStudentNumber(),
                Course.getRate(),
                Course.getGoals()
        );
    }
}
