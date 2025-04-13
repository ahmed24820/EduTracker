package com.Graduation.EduTracker.Mapper;

import com.Graduation.EduTracker.Dtos.QuestionDto;
import com.Graduation.EduTracker.Models.Question;
import org.mapstruct.Mapper;

@Mapper(uses = AnswerMapper.class)
public interface QuestionMapper {
    QuestionDto map (Question Question);
    Question unMap(QuestionDto QuestionDto);
}
