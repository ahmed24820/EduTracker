package com.Graduation.EduTracker.Mapper;

import com.Graduation.EduTracker.Dtos.AnswerDto;
import com.Graduation.EduTracker.Models.Answer;
import org.mapstruct.Mapper;

@Mapper
public interface AnswerMapper {
    AnswerDto map (Answer Answer);
    Answer unMap(AnswerDto AnswerDto);
}
