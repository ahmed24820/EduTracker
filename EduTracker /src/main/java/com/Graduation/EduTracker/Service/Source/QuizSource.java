package com.Graduation.EduTracker.Service.Source;

import com.Graduation.EduTracker.Models.Quiz;

public interface QuizSource {

    Quiz save(Quiz quiz) throws Exception;
    void delete (long id);
    void update (long id);
}
