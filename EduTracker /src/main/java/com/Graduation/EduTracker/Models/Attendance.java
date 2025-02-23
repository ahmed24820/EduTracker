package com.Graduation.EduTracker.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Table
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Attendance {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private List<User> Users;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private AttendanceStatue statue;
}
