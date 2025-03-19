package com.Graduation.EduTracker.Service.Source;

import com.Graduation.EduTracker.Models.Questions;

import java.util.Optional;

public interface questionSource {

    Questions addQuestion(Questions questions);
    void delete (long id);
    void update (long id);
    Optional<Questions> findById (long id);
}

