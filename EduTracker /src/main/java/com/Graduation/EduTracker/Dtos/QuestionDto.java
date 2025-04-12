package com.Graduation.EduTracker.Dtos;

import lombok.*;

import java.util.List;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private Long id;
    private String text;
    private List<AnswerDto> answers;
    private Integer correctAnswerIndex;
    private Long QuizId;
}
