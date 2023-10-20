package com.hieuph.todosmanagement.controller.v1;

import com.hieuph.todosmanagement.dto.request.PasswordDto;
import com.hieuph.todosmanagement.dto.response.MessageResponse;
import com.hieuph.todosmanagement.dto.response.UserI4Response;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.exception.BadRequestException;
import com.hieuph.todosmanagement.security.service.UserDetailImpl;
import com.hieuph.todosmanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/user")
@RestController("user/v1")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    private Authentication authentication;
    @GetMapping("")
    public ResponseEntity<?> getInfoUser(){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        User user = ((UserDetailImpl) authentication.getPrincipal()).getUser();
        return ResponseEntity.ok(new UserI4Response(user.getId(), user.getUsername(), user.getEmail(), user.isEnabled(), user.getRoles()));
    }
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordDto passwordDto){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        User user = ((UserDetailImpl) authentication.getPrincipal()).getUser();
        if(!userService.oldPasswordIsValid(user, passwordDto.getOldPassword())){
            return ResponseEntity.badRequest().body(new MessageResponse("Incorrect old password!"));
        }
        userService.changePassword(user,passwordDto.getNewPassword());
        return ResponseEntity.ok().body(new MessageResponse("Password changed successfully!"));
    }

}
