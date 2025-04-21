package com.platform.platform.repo;

import com.platform.platform.entity.Question;
import com.platform.platform.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    List<Quiz> findBySubject(String subject);
    List<Quiz> findByTeacherId(int teacherId);
    Quiz findTopByOrderByIdDesc();

}
