package com.Graduation.EduTracker.Repos;

import com.Graduation.EduTracker.Models.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepo extends JpaRepository<Questions,Long> {
}
