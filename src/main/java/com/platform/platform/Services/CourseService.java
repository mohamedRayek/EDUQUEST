package com.platform.platform.Services;

import com.platform.platform.entity.Course;
import com.platform.platform.entity.dto.CourseDTO;
import com.platform.platform.entity.dto.CourseDTOMapper;
import com.platform.platform.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseDTOMapper courseDTOMapper;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseDTOMapper)
                .collect(Collectors.toList());

    }

    public Optional<CourseDTO> getCourseById(int id) {
        return courseRepository.findById(id)
                .map(courseDTOMapper);
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }

    public Course updateCourse(int id, Course courseDetails) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setTitle(courseDetails.getTitle());
        course.setSubTitle(courseDetails.getSubTitle());
        course.setDescription(courseDetails.getDescription());
        course.setPrice(courseDetails.getPrice());
        return courseRepository.save(course);
    }
}
