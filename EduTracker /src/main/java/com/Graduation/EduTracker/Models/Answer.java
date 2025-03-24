package com.Graduation.EduTracker.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
@RequiredArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private boolean isCorrect;
    @ManyToOne
    @JoinColumn(name = "Question_id")
    private Question question;

}
