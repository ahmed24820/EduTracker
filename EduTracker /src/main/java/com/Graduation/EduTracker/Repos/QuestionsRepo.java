package com.Graduation.EduTracker.Repos;

import com.Graduation.EduTracker.Models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepo extends JpaRepository<Question,Long> {
}
