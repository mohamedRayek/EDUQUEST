package com.platform.platform.repo;

import com.platform.platform.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Teacher findByEmail(String email);
    boolean existsByEmail(String email);
}
