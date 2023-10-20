package com.hieuph.todosmanagement.event.listener;

import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.event.OnRegistrationCompleteEvent;
import com.hieuph.todosmanagement.service.EmailService;
import com.hieuph.todosmanagement.service.VerificationTokenService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private EmailService emailService;

    private User user;
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event ) {
        user = event.getUser();
        String token = UUID.randomUUID().toString();
        verificationTokenService.SavedVerificationToken(user, token);
        String url =event.getApplicationUrl()+"/api/auth/register/verify-email?token="+token;
        try {
            sendVerificationMail(url);
        }catch (MessagingException | UnsupportedEncodingException exception){
            throw new RuntimeException(exception);
        }
        log.info("Click the link to verify your registration :  {}", url);

    }

    public void sendVerificationMail(String url) throws MessagingException, UnsupportedEncodingException {
        emailService.sendVerificationEmail(user, url);
    }
}
