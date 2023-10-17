package com.hieuph.todosmanagement.controller;

import com.hieuph.todosmanagement.dto.request.LoginDto;
import com.hieuph.todosmanagement.dto.request.PasswordDto;
import com.hieuph.todosmanagement.dto.request.UserDto;
import com.hieuph.todosmanagement.dto.response.MessageResponse;
import com.hieuph.todosmanagement.dto.response.UserI4Response;
import com.hieuph.todosmanagement.entity.PasswordResetToken;
import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.entity.VerificationToken;
import com.hieuph.todosmanagement.event.OnRegistrationCompleteEvent;
import com.hieuph.todosmanagement.exception.UserAlreadyExistsException;
import com.hieuph.todosmanagement.security.jwt.JwtTokenService;
import com.hieuph.todosmanagement.security.service.UserDetailImpl;
import com.hieuph.todosmanagement.service.EmailService;
import com.hieuph.todosmanagement.service.PasswordResetTokenService;
import com.hieuph.todosmanagement.service.UserService;
import com.hieuph.todosmanagement.service.VerificationTokenService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api/auth")
@RestController
@Slf4j
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @PostMapping("login")
    public  ResponseEntity<?> logIn(
             @Valid @RequestBody LoginDto loginDto, final HttpServletRequest request)
        throws MessagingException, UnsupportedEncodingException{
        User user =userService.getUserByUsername(loginDto.getUsername());
        if(user == null || user.isEnabled() == false){
            return ResponseEntity.badRequest().body(new MessageResponse("User does not exist or not actived!"));
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
            ResponseCookie jwtCookie =  jwtTokenService.generateJwtCookie(userDetail);
            List<String> roles =userDetail.getAuthorities().stream()
                    .map(i -> i.getAuthority())
                    .collect(Collectors.toList());
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(new UserI4Response(userDetail.getUser()));
        }
        catch (Exception exception){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid username or password!"));

        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logOut(){
        ResponseCookie cookie = jwtTokenService.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've bean log out!"));
    }


    @PostMapping("/reset-password-request")
    @PreAuthorize("permitAll")
    public ResponseEntity<?> resetPasswordRequest(@RequestBody PasswordDto passwordDto, final HttpServletRequest request)
        throws MessagingException, UnsupportedEncodingException{
        System.out.println(passwordDto.getEmail());
        Optional<User> user = userService.getUserByEmail(passwordDto.getEmail());
        if(!user.isPresent()){
            System.out.println("null"+passwordDto.getEmail());
        }
        PasswordResetToken oldToken =passwordResetTokenService.IsHaving(passwordDto.getEmail());
        if(oldToken == null){
            if(user.isPresent()){
                String passwordResetToken = UUID.randomUUID().toString();
                passwordResetTokenService.createPasswordResetTokenForUser(user.get(), passwordResetToken);
                passwordResetEmailLink(user.get(), applicationUrl(request), passwordResetToken);
                return ResponseEntity.ok().body(new MessageResponse("Check your email!"));
            }

        }
        else {
            PasswordResetToken newToken = passwordResetTokenService.generateNewResetPasswordToken(oldToken.getToken());
            User tokenUser = newToken.getUser();
            passwordResetEmailLink(tokenUser, applicationUrl(request), newToken.getToken());
            return ResponseEntity.ok().body(new MessageResponse("Check your email!"));
        }
        return ResponseEntity.badRequest().body("Error!");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordDto passwordDto, @RequestParam("token") String token){
        String tokenVerificationRs = passwordResetTokenService.validateToken(token);
        if(!tokenVerificationRs.equalsIgnoreCase("valid")){
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid password reset token!"));
        }
        User user = userService.findUserByPasswordToken(token);
        if(user!=null){
            userService.changePassword(user, passwordDto.getNewPassword());
            return ResponseEntity.ok(new MessageResponse("Change Password is successfully!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Invalid password reset token!"));
    }

    public void passwordResetEmailLink(User user, String applicationUrl, String passwordToken)
        throws MessagingException, UnsupportedEncodingException{
        String url ="http://localhost:3000/reset-password?token="+passwordToken;
        emailService.sendPasswordResetVerificationEmail(user, url);
        log.info("Click the link to reset password: {}", url);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto, final HttpServletRequest request, BindingResult result){
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(new MessageResponse(result.toString()));
        }
        if (userService.getUserByEmail(userDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User with email " + userDto.getEmail()+" already exist!");
        }
        User user = userService.register(userDto);
        publisher.publishEvent(new OnRegistrationCompleteEvent(user, applicationUrl(request)));
        return ResponseEntity.ok(new MessageResponse("Registed! Pls check mail to activation account!"));
    }
    @GetMapping("/register/verify-email")
    public ResponseEntity<?> verificationEmail(@RequestParam("token") String token, final HttpServletRequest request){
        String url = applicationUrl(request)+"/api/auth/register/resend-verification-token?token="+token;
        VerificationToken verificationToken = verificationTokenService.findToken(token);
        if(verificationToken.getUser().isEnabled()){
            return ResponseEntity.badRequest().body(new MessageResponse("This account has aready verified. Pls login!"));
        }
        String verificationResult =verificationTokenService.validateToken(token);
        if(verificationResult.equalsIgnoreCase("valid")){
            return ResponseEntity.ok(new MessageResponse("Email verified successful! Now you can login to your account"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Activation Link is expired! Pls subcribe for new link"));
    }
    @GetMapping("/register/resend-verification-token")
    public ResponseEntity<?> resendVerificationToken(@RequestParam("token") String oldToken, final HttpServletRequest request)
        throws MessagingException, UnsupportedEncodingException{
        var verificationToken = verificationTokenService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerificationMail(user, applicationUrl(request), verificationToken);
        return ResponseEntity.ok()
                .body(new MessageResponse("A new verification link has been sent to your email, please, check to activate your account"));
    }

    public void resendVerificationMail(User tUser, String applicationUrl, VerificationToken verificationToken)
            throws MessagingException, UnsupportedEncodingException{
        String url = applicationUrl + "/api/auth/register/verify-email?token="+verificationToken.getToken();
        emailService.sendVerificationEmail(tUser, url);
        log.info("Click the link to verify your registration :  {}", url);
    }

    public String applicationUrl(HttpServletRequest request){
        return "http://"+ request.getServerName() + ":" +request.getServerPort() + request.getContextPath();
    }

}
