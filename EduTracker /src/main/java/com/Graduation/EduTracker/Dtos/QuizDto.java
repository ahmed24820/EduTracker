package com.Graduation.EduTracker.Dtos;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private Long id;
    private String title;
    private String description;
    private List<QuestionDto> questions;
}
