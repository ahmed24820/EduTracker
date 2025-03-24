package com.Graduation.EduTracker.Service.Source;

import com.Graduation.EduTracker.Models.Quiz;

import java.util.Optional;

public interface QuizSource {

    Quiz save(Quiz quiz) throws Exception;
    void delete (long id);
    void update (long id);
    Optional<Quiz> findById(long id);
}
