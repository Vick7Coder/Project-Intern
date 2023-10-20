package com.hieuph.todosmanagement.service;

import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.entity.VerificationToken;

import javax.xml.transform.sax.SAXResult;

public interface VerificationTokenService {

    void SavedVerificationToken(User user, String verificationToken);
    String validateToken(String tToken);

    VerificationToken generateNewVerificationToken(String oldToken);

    VerificationToken findToken(String token);
}
