package com.Graduation.EduTracker.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String text;

    @Enumerated(EnumType.STRING)
    private Q_type type;

    @JsonBackReference
    @OneToMany(orphanRemoval = true , mappedBy = "question" )
    private List<Answer> answers;

    private int correctAnswerIndex;

    @ManyToOne
    @JoinColumn(name = "quiz_id" , nullable = false)
    private Quiz quiz;
}
