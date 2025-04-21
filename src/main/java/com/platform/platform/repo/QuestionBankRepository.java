package com.platform.platform.repo;

import com.platform.platform.entity.QuestionBank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionBankRepository extends JpaRepository<QuestionBank, Integer> {

    List<QuestionBank> findBySubject(String subject);
}
