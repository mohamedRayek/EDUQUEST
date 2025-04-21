package com.platform.platform.entity.dto;

import com.platform.platform.entity.Question;
import com.platform.platform.entity.QuestionBank;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QuestionBankDTOMapper implements Function<QuestionBank,QuestionBankDTO> {
 @Override
  public QuestionBankDTO apply(QuestionBank questionBank) {
       return null;
//        new QuestionBankDTO(
//                questionBank.getId(),
//                questionBank.getSubject(),
//                questionBank.getQuestions() != null
//                        ? questionBank.getQuestions().stream()
//                        .map(Question::getQuestion)
//                        .collect(Collectors.toList())
//                        : Collections.emptyList()
//
//        );
    }
}
