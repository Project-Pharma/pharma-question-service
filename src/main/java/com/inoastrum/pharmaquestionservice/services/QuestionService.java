package com.inoastrum.pharmaquestionservice.services;

import com.inoastrum.pharmaquestionservice.web.model.QuestionDto;

import java.util.List;
import java.util.UUID;

public interface QuestionService {

    QuestionDto findQuestionDtoById(UUID questionId);

    QuestionDto saveNewQuestion(QuestionDto questionDto);

    void updateQuestion(UUID questionId, QuestionDto questionDto);

    void deleteQuestionById(UUID questionId);

    List<QuestionDto> findQuestionsByPharmacyId(UUID pharmacyId);
}
