package com.platform.platform.repo;

import com.platform.platform.entity.Student;
import com.platform.platform.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByEmail(String email);
    boolean existsByEmail(String email);
}
