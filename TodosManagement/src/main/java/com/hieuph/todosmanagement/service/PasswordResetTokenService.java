package com.hieuph.todosmanagement.service;

import com.hieuph.todosmanagement.entity.PasswordResetToken;
import com.hieuph.todosmanagement.entity.User;

public interface PasswordResetTokenService {
    String validateToken(String token);
    void createPasswordResetTokenForUser(User user, String passwordResetToken);
    PasswordResetToken findPasswordResetToken(String token);
    PasswordResetToken IsHaving(String email);

    PasswordResetToken generateNewResetPasswordToken(String oldToken);
}
