package com.hieuph.todosmanagement.service.Impl;

import com.hieuph.todosmanagement.entity.PasswordResetToken;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.repository.PasswordResetTokenRepository;
import com.hieuph.todosmanagement.service.PasswordResetTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
@Slf4j
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Override
    public String validateToken(String token) {
        PasswordResetToken ptoken = passwordResetTokenRepository.findByToken(token);
        if (ptoken == null){
            return "Invalid Password Reset Token!";
        }
        Calendar cal = Calendar.getInstance();
        if (ptoken.getExpirationTime().getTime() - cal.getTime().getTime() <=0){
            return "Token is expired";
        }
        return "valid";
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordResetToken) {
        PasswordResetToken nToken =new PasswordResetToken(passwordResetToken, user);
        passwordResetTokenRepository.save(nToken);
    }

    @Override
    public PasswordResetToken findPasswordResetToken(String token) {
        PasswordResetToken t = passwordResetTokenRepository.findByToken(token);
        return t;
    }

    @Override
    public PasswordResetToken IsHaving(String email) {
        return passwordResetTokenRepository.findByUserEmail(email);
    }

    @Override
    public PasswordResetToken generateNewResetPasswordToken(String oldToken) {
        var pToken = passwordResetTokenRepository.findByToken(oldToken);
        var pTokenTime = new PasswordResetToken();
        pToken.setToken(UUID.randomUUID().toString());
        pToken.setExpirationTime(pTokenTime.getTokenExpirationTime());
        passwordResetTokenRepository.save(pToken);
        return pToken;
    }
}
