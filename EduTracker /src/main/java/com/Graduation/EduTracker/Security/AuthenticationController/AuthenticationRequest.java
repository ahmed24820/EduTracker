package com.Graduation.EduTracker.Security.AuthenticationController;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class AuthenticationRequest {
    private String email;
    private String password;

}
