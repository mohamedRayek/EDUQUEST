package com.platform.platform.controller;

import com.platform.platform.Services.QuizService;
import com.platform.platform.entity.Question;
import com.platform.platform.entity.Quiz;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    // إنشاء امتحان جديد
    @PostMapping("/create")
    public Quiz createNewQuiz(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String subject = request.get("subject");
        return quizService.createNewQuiz(name, subject);
    }

    // الحصول على الامتحان النشط
    @GetMapping("/active")
    public Quiz getActiveQuiz() {
        return quizService.getActiveQuiz();
    }

    // إضافة سؤال إلى الامتحان النشط
    @PostMapping("/questions")
    public Question addQuestionToActiveQuiz(@RequestBody Question question) {
        return quizService.addQuestionToActiveQuiz(question);
    }

    // حذف سؤال من الامتحان النشط
    @DeleteMapping("/questions/{questionId}")
    public void deleteQuestionFromActiveQuiz(@PathVariable int questionId) {
        quizService.deleteQuestionFromActiveQuiz(questionId);
    }

    // تعديل سؤال في الامتحان النشط
    @PutMapping("/questions/{questionId}")
    public Question updateQuestionInActiveQuiz(
            @PathVariable int questionId,
            @RequestBody Question updatedQuestion) {
        return quizService.updateQuestionInActiveQuiz(questionId, updatedQuestion);
    }

    // الحصول على جميع الأسئلة في الامتحان النشط
    @GetMapping("/questions")
    public List<Question> getQuestionsInActiveQuiz() {
        return quizService.getQuestionsInActiveQuiz();
    }

    // حساب نتيجة الامتحان
    // Calculate quiz result
    @PostMapping("/submit")
    public ResponseEntity<QuizResult> calculateQuizResult(@RequestBody List<StudentAnswer> studentAnswers) {
        try {
            Quiz activeQuiz = quizService.getActiveQuiz();
            if (activeQuiz == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new QuizResult("No active quiz found", 0, 0, 0,0));
            }

            List<Question> allQuestions = quizService.getQuestionsByQuizId(activeQuiz.getId());
            Map<Integer, Question> questionMap = allQuestions.stream()
                    .collect(Collectors.toMap(Question::getId, Function.identity()));

            int totalQuestions = allQuestions.size();
            int correctCount = 0;
            int attemptedQuestions = 0;

            for (StudentAnswer answer : studentAnswers) {
                Question question = questionMap.get(answer.getQuestionId());
                if (question != null) {
                    attemptedQuestions++;
                    if (Character.toLowerCase(question.getRightChoice()) ==
                            Character.toLowerCase(answer.getSelectedAnswer())) {
                        correctCount++;
                    }
                }
            }

            double scorePercentage = totalQuestions > 0 ?
                    ((double) correctCount / totalQuestions) * 100 : 0;

            return ResponseEntity.ok(new QuizResult(
                    "Quiz results calculated",
                    correctCount,
                    totalQuestions,
                    scorePercentage,
                    attemptedQuestions
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new QuizResult("Error calculating results: " + e.getMessage(),
                            0, 0, 0,0));
        }
    }

    @Data
    public static class StudentAnswer {
        private int questionId;  // Changed from Question object to questionId
        private char selectedAnswer;
    }

    @Data
    public static class QuizResult {
        private String message;
        private int correctAnswers;
        private int totalQuestions;
        private double scorePercentage;
        private int attemptedQuestions;

        public QuizResult(String message, int correctAnswers,
                          int totalQuestions, double scorePercentage,
                          int attemptedQuestions) {
            this.message = message;
            this.correctAnswers = correctAnswers;
            this.totalQuestions = totalQuestions;
            this.scorePercentage = scorePercentage;
            this.attemptedQuestions = attemptedQuestions;
        }
    }
}
