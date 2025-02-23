package com.Graduation.EduTracker.Email.Service;

public interface EmailResource {
    void SendConfirmationEmail(String from , String to , String token) throws Exception;
}
