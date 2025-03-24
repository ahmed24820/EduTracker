package com.Graduation.EduTracker.Service.Source;

import com.Graduation.EduTracker.Models.Question;

import java.util.Optional;

public interface questionSource {

    Question addQuestion(Question question);
    void delete (long id);
    void update (long id);
    Optional<Question> findById (long id);
}

