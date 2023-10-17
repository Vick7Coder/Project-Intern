package com.hieuph.todosmanagement.service;

import com.hieuph.todosmanagement.dto.request.UserDto;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.entity.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register(UserDto userDto);


    User getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    List<User> getAll();



    void changePassword(User theUser, String newPassword);

    User findUserByPasswordToken(String token);

    boolean oldPasswordIsValid(User user, String oldPassword);

}
