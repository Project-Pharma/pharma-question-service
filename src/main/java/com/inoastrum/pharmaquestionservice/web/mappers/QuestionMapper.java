package com.inoastrum.pharmaquestionservice.web.mappers;

import com.inoastrum.pharmaquestionservice.domain.Question;
import com.inoastrum.pharmaquestionservice.web.model.QuestionDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface QuestionMapper {

    QuestionDto questionToQuestionDto(Question question);

    Question questionDtoToQuestion(QuestionDto questionDto);
}
