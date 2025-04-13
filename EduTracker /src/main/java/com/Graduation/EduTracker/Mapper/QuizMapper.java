package com.Graduation.EduTracker.Mapper;

import com.Graduation.EduTracker.Dtos.QuizDto;
import com.Graduation.EduTracker.Models.Quiz;
import org.mapstruct.Mapper;

@Mapper(uses = QuestionMapper.class)
public interface QuizMapper {
    QuizDto map (Quiz quiz);
    Quiz unMap(QuizDto quizDto);
}
