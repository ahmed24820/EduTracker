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
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String Description;
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER , mappedBy = "quiz" ,orphanRemoval = true)
    private List<Question> Question;
    private String MadeBy;
}
