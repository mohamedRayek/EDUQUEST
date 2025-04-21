package com.platform.platform.repo;

import com.platform.platform.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {

    Page<Course> findBySubjectContainingIgnoreCaseAndTeacherIdNot(
            String subject, Integer teacherId, Pageable pageable);
}
