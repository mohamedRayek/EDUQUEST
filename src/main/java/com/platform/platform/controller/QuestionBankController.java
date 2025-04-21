package com.platform.platform.controller;

import com.platform.platform.Services.QuestionBankService;
import com.platform.platform.entity.QuestionBank;
import com.platform.platform.entity.dto.QuestionBankDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question-banks")
public class QuestionBankController {

    @Autowired
    private QuestionBankService questionBankService;
    // الحصول على بنوك الأسئلة بواسطة المادة
    @GetMapping("/subject/{subject}")
    public List<QuestionBank> getQuestionBanksBySubject(@PathVariable String subject) {
        return questionBankService.getQuestionBanksBySubject(subject);
    }

}
