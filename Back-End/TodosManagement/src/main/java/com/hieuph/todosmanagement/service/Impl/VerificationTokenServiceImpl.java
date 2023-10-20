package com.hieuph.todosmanagement.service.Impl;

import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.entity.VerificationToken;
import com.hieuph.todosmanagement.repository.UserRepository;
import com.hieuph.todosmanagement.repository.VerificationTokenRepository;
import com.hieuph.todosmanagement.service.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void SavedVerificationToken(User user, String verificationToken) {
        var verToken = new VerificationToken(verificationToken, user);
        verificationTokenRepository.save(verToken);
    }

    @Override
    public String validateToken(String tToken) {
        VerificationToken vToken = verificationTokenRepository.findByToken(tToken);
        if (vToken==null){
            return "Invalid verification token!";
        }
        User user = vToken.getUser();
        Calendar cal = Calendar.getInstance();
        if(vToken.getExpirationTime().getTime() - cal.getTime().getTime() <= 0){
            return "Token aready expired!";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        var verificationToken = verificationTokenRepository.findByToken(oldToken);
        var newVerTkTime = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(newVerTkTime.getTokenExpirationTime());
        return verificationTokenRepository.save(verificationToken);
    }

    @Override
    public VerificationToken findToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }
}
