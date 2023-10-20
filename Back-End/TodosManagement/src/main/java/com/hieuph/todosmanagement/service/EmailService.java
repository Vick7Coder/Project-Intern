package com.hieuph.todosmanagement.service;

import com.hieuph.todosmanagement.entity.User;

public interface EmailService {
    void sendVerificationEmail(User user, String url);
    void sendPasswordResetVerificationEmail(User user, String url);

}
