package com.hieuph.todosmanagement.service.Impl;

import com.hieuph.todosmanagement.Filter.User.GenericUserSpecification;
import com.hieuph.todosmanagement.Filter.User.UserFilter;
import com.hieuph.todosmanagement.dto.request.Paging.Pagination;
import com.hieuph.todosmanagement.dto.request.Paging.PagingRequest;
import com.hieuph.todosmanagement.dto.request.UserDto;
import com.hieuph.todosmanagement.entity.*;
import com.hieuph.todosmanagement.exception.NotFoundException;
import com.hieuph.todosmanagement.repository.RoleRepository;
import com.hieuph.todosmanagement.repository.UserRepository;
import com.hieuph.todosmanagement.service.PasswordResetTokenService;
import com.hieuph.todosmanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private GenericUserSpecification genericUserSpecification;
    @Override
    public User register(UserDto userDto) {

        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(()->new NotFoundException("Role is not found!"));
        roles.add(userRole);
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Invalid User!"));
        return user;
    }



    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAll(UserFilter userFilter, PagingRequest pagingRequest) {
        List<User> userList = userRepository
                .findAll(genericUserSpecification.generic(userFilter), Pagination.initPageable(pagingRequest))
                .getContent();

        List<User> respList =   new ArrayList<>();
        if(!userList.isEmpty()){
            for(int i =0; i < userList.size(); i++){
                System.out.println(userList.get(i).toString());
                respList.add(userList.get(i));
            }
            return respList;
        }
        return null;
    }

    @Override
    public void changePassword(User theUser, String newPassword) {
        theUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(theUser);

    }

    @Override
    public User findUserByPasswordToken(String token) {
        PasswordResetToken pToken = passwordResetTokenService.findPasswordResetToken(token);
        return pToken.getUser();
    }

    @Override
    public boolean oldPasswordIsValid(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public Long count(UserFilter userFilter) {
        return userRepository.count(genericUserSpecification.generic(userFilter));
    }
}
