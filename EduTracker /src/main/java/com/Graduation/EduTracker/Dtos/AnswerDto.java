package com.Graduation.EduTracker.Dtos;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private Long id;
    private String text;
}
