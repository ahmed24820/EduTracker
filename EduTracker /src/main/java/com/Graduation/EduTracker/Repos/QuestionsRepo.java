package com.Graduation.EduTracker.Repos;

import com.Graduation.EduTracker.Dtos.QuestionDto;
import com.Graduation.EduTracker.Models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionsRepo extends JpaRepository<Question,Long> {
    Optional<Question> findByQuizId(Long quizId);
}
