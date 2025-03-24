package com.Graduation.EduTracker.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class QuestionDto {
    private Long id;
    private String text;
    private List<AnswerDto> answers;
    private Integer correctAnswerIndex;
}
