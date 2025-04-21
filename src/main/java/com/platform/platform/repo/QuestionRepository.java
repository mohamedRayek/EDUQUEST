package com.platform.platform.repo;

import com.platform.platform.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findBySubject(String subject);
    List<Question> findByLessonName(String lessonName);
    List<Question> findByCourse(String course);
    List<Question> findByQuizId(int quizId); // Changed from getQuestionsByQuizId
}
