package com.platform.platform.Services;

import com.platform.platform.entity.Question;
import com.platform.platform.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    // إنشاء سؤال جديد
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question updateQuestion(int id, Question updatedQuestion) {
        Question existingQuestion = questionRepository.findById(id).orElse(null);
        if (existingQuestion != null) {
            // تحديث الحقول المطلوبة
            existingQuestion.setQuestion(updatedQuestion.getQuestion());
            existingQuestion.setOption1(updatedQuestion.getOption1());
            existingQuestion.setOption2(updatedQuestion.getOption2());
            existingQuestion.setOption3(updatedQuestion.getOption3());
            existingQuestion.setOption4(updatedQuestion.getOption4());
            existingQuestion.setRightChoice(updatedQuestion.getRightChoice());
            existingQuestion.setSubject(updatedQuestion.getSubject());
            existingQuestion.setLessonName(updatedQuestion.getLessonName());
            existingQuestion.setCourse(updatedQuestion.getCourse());

            // حفظ التحديثات
            return questionRepository.save(existingQuestion);
        } else {
            // إذا لم يتم العثور على السؤال
            return null;
        }
    }

    public void deleteQuestion(int id) {
        questionRepository.deleteById(id);
    }




}
