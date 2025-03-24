package com.Graduation.EduTracker.Service.Source;

import com.Graduation.EduTracker.Models.Answer;

import java.util.Optional;

public interface AnswerSource {

    Answer addAnswer(Answer answer);
    void delete (long id);
    void update (long id);
    Optional<Answer> findById (long id);
}
