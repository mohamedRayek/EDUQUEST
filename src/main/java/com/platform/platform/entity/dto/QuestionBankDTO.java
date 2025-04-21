package com.platform.platform.entity.dto;

import com.platform.platform.entity.Question;

import java.util.List;

public record QuestionBankDTO (
        Integer id,
        String subject,
        List<Question> questions
){
}
