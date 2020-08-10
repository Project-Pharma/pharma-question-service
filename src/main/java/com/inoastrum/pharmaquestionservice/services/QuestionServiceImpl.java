package com.inoastrum.pharmaquestionservice.services;

import com.inoastrum.pharmaquestionservice.domain.Question;
import com.inoastrum.pharmaquestionservice.exceptions.QuestionNotFoundException;
import com.inoastrum.pharmaquestionservice.repositories.QuestionRepository;
import com.inoastrum.pharmaquestionservice.web.mappers.QuestionMapper;
import com.inoastrum.pharmaquestionservice.web.model.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Override
    public QuestionDto findQuestionDtoById(UUID questionId) {
        return questionMapper.questionToQuestionDto(
                questionRepository.findById(questionId)
                        .orElseThrow(QuestionNotFoundException::new));
    }

    @Override
    public QuestionDto saveNewQuestion(QuestionDto questionDto) {
        return questionMapper.questionToQuestionDto(
                        questionRepository.save(
                                questionMapper.questionDtoToQuestion(
                                        questionDto
                                )
                        ));
    }

    @Override
    public void updateQuestion(UUID questionId, QuestionDto questionDto) {
        Question question = questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);

        if (questionDto.getQuestionImageLinks() != null && !questionDto.getQuestionImageLinks().isEmpty()) {
            question.setQuestionImageLinks(questionDto.getQuestionImageLinks());
        }

        question.setPharmacyId(questionDto.getPharmacyId());
        question.setQuestionText(questionDto.getQuestionText());

        questionRepository.save(question);
    }

    @Override
    public void deleteQuestionById(UUID questionId) {
        questionRepository.deleteById(questionId);
    }

    @Override
    public List<QuestionDto> findQuestionsByPharmacyId(UUID pharmacyId) {
        return questionRepository.findAllByPharmacyId(pharmacyId).stream()
                .map(questionMapper::questionToQuestionDto)
                .collect(Collectors.toList());
    }
}
