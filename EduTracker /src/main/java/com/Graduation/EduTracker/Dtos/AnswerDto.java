package com.Graduation.EduTracker.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
public class AnswerDto {
    private Long id;
    private String text;
}
