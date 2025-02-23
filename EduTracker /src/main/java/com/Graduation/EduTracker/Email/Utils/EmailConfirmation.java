package com.Graduation.EduTracker.Email.Utils;

import com.Graduation.EduTracker.Models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String Token;
    // the user has only one confirmation mail in registration
    @OneToOne
    @JoinColumn(name = "User_id")
    private User user;

    private LocalDate date;

    public EmailConfirmation(User user){
        this.user = user;
        this.Token = UUID.randomUUID().toString(); // generate an automatic random id
        this.date = LocalDate.now();
    }
}
