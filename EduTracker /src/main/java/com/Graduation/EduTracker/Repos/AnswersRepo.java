package com.Graduation.EduTracker.Repos;

import com.Graduation.EduTracker.Models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswersRepo extends JpaRepository<Answer,Long> {
}
