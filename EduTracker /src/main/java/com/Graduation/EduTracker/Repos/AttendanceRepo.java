package com.Graduation.EduTracker.Repos;

import com.Graduation.EduTracker.Models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepo extends JpaRepository<Attendance,Long> {
}
