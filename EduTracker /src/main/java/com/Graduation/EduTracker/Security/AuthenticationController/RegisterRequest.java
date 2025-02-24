package com.Graduation.EduTracker.Security.AuthenticationController;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String firstname;
    private String Lastname;
    private String username;
    private String Password;
    private String phoneNumber;
    private String role;


    public RegisterRequest() {

    }

    public String getfirstname() {
        return firstname;
    }
    public String getusername(){
        return username;
    }

}
