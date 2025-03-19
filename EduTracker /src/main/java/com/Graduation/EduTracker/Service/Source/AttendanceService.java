package com.Graduation.EduTracker.Service.Source;

import com.Graduation.EduTracker.Models.Attendance;

import java.time.LocalDate;

public interface AttendanceService {

    void saveAttendance();
    void removeStudentAttendance(long id);
    Attendance getAttendanceByDate(LocalDate date);

}
