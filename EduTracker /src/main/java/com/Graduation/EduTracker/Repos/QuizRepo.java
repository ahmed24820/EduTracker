package com.Graduation.EduTracker.Repos;

import com.Graduation.EduTracker.Models.Attendance;
import com.Graduation.EduTracker.Models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepo extends JpaRepository<Quiz,Long> {
}
