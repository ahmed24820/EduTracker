package com.Graduation.EduTracker.Email.Service;

import com.Graduation.EduTracker.Email.Repo.EmailRepo;
import com.Graduation.EduTracker.Email.Utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailService implements EmailResource {
    // connector with a database
    private final EmailRepo emailRepo;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String userName;

    // we need to sync this method to be more efficient in use
    @Override
    @Async
    public void SendConfirmationEmail(String from, String to, String token) throws Exception {
    try
        {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("New Verification Message");
            message.setFrom(userName);
            message.setTo(to);
            message.setText(Utils.sendMessage(from, host, token));
            javaMailSender.send(message);

        } catch (Exception e){
          log.error(e.getMessage());
          throw new Exception(e.getMessage());
       }
    }

}
