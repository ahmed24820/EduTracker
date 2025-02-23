package com.Graduation.EduTracker.Security.AuthenticationController;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Setter
@Getter
@AllArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String phoneNumber;
    private String role;


    public RegisterRequest() {

    }
}
