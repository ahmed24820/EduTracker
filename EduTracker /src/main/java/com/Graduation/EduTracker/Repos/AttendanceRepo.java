package com.Graduation.EduTracker.Repos;

import com.Graduation.EduTracker.Models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance,Long> {
}
