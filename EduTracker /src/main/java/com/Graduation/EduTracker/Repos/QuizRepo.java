package com.Graduation.EduTracker.Repos;

import com.Graduation.EduTracker.Models.Attendance;
import com.Graduation.EduTracker.Models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepo extends JpaRepository<Quiz,Long> {
}
