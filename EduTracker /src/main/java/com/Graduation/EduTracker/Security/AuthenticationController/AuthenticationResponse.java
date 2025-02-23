package com.Graduation.EduTracker.Security.AuthenticationController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Setter
@Getter
@AllArgsConstructor
public class AuthenticationResponse {
    private String access_token;
    private String Refresh_token;
}
