package com.Graduation.EduTracker.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
public class QuizDto {
    private Long id;
    private String title;
    private String description;
    private List<QuestionDto> questions;
}
