package com.Graduation.EduTracker.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@Setter
@Getter
@RequiredArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id" , nullable = false)
    private Quiz quiz;
    private String QuestionName;
    @Enumerated(EnumType.STRING)
    private Q_type type;
    @OneToMany
    private List<Answer> answers;
}
