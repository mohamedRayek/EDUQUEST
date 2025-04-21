package com.platform.platform.Services;

import com.platform.platform.entity.Question;
import com.platform.platform.entity.QuestionBank;
import com.platform.platform.entity.Quiz;
import com.platform.platform.repo.QuestionBankRepository;
import com.platform.platform.repo.QuestionRepository;
import com.platform.platform.repo.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionBankRepository questionBankRepository;

    private Quiz activeQuiz;


    public Quiz createNewQuiz(String name, String subject) {
        Quiz quiz = new Quiz();
        quiz.setName(name);
        quiz.setSubject(subject);

        QuestionBank questionBank = new QuestionBank();
        questionBank.setSubject(subject);
        questionBankRepository.save(questionBank);

        quiz.setQuestionBank(questionBank);
        activeQuiz = quizRepository.save(quiz);
        return activeQuiz;
    }


    public Quiz getActiveQuiz() {
        return activeQuiz;
    }


    @Transactional
    public Question addQuestionToActiveQuiz(Question question) {
        if (activeQuiz != null) {
            question.setQuiz(activeQuiz);
            question.setQuestionBank(activeQuiz.getQuestionBank());
            return questionRepository.save(question);
        }
        throw new RuntimeException("No active quiz found");
    }


    public void deleteQuestionFromActiveQuiz(int questionId) {
        if (activeQuiz != null) {
            Question question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new RuntimeException("Question not found"));
            if (question.getQuiz().getId() == activeQuiz.getId()) {
                questionRepository.delete(question);
            } else {
                throw new RuntimeException("Question doesn't belong to active quiz");
            }
        } else {
            throw new RuntimeException("No active quiz found");
        }
    }


    public Question updateQuestionInActiveQuiz(int questionId, Question updatedQuestion) {
        if (activeQuiz != null) {
            Question existing = questionRepository.findById(questionId)
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            if (existing.getQuiz().getId() == activeQuiz.getId()) {
                existing.setQuestion(updatedQuestion.getQuestion());
                existing.setOption1(updatedQuestion.getOption1());
                existing.setOption2(updatedQuestion.getOption2());
                existing.setOption3(updatedQuestion.getOption3());
                existing.setOption4(updatedQuestion.getOption4());
                existing.setRightChoice(updatedQuestion.getRightChoice());
                existing.setSubject(updatedQuestion.getSubject());
                existing.setLessonName(updatedQuestion.getLessonName());
                existing.setCourse(updatedQuestion.getCourse());
                return questionRepository.save(existing);
            }
            throw new RuntimeException("Question doesn't belong to active quiz");
        }
        throw new RuntimeException("No active quiz found");
    }


    public List<Question> getQuestionsInActiveQuiz() {
        if (activeQuiz != null) {
            return questionRepository.findByQuizId(activeQuiz.getId());
        }
        throw new RuntimeException("No active quiz found");
    }


    public List<Question> getQuestionsByQuizId(int quizId) {
        return questionRepository.findByQuizId(quizId);
    }


    public Question getQuestionById(int questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }

    public class QuestionNotFoundException extends RuntimeException {
        public QuestionNotFoundException(String message) {
            super(message);
        }


    }
}
