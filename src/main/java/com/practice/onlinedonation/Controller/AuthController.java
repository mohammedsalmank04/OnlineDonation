package com.practice.onlinedonation.Controller;

import com.practice.onlinedonation.Exception.ApiException;
import com.practice.onlinedonation.Repository.UserRepository;
import com.practice.onlinedonation.Service.AuthenticateService;
import com.practice.onlinedonation.payload.UserResponseDTO;
import com.practice.onlinedonation.security.jwt.JwtService;
import com.practice.onlinedonation.security.payload.LoginRequest;
import com.practice.onlinedonation.security.payload.LoginResponse;
import com.practice.onlinedonation.security.payload.SignUpRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {
    @Autowired
    private AuthenticateService authenticateService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    //Register new user using email as a unique element
    @PostMapping("/auth/signUp")
    public ResponseEntity<LoginResponse> register(@RequestBody SignUpRequest user){
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ApiException("User " + user.getEmail() +" Already exists" );

        }
        LoginResponse loginResponse = authenticateService.signUp(user);
        return new ResponseEntity<>(loginResponse,HttpStatus.CREATED );

    }

    //Login user using cookies
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return authenticateService.loginUsingCookie(loginRequest);

    }

    //Get the current logged-in user object
    @GetMapping("/api/auth/me")
    public ResponseEntity<?> currentUser(){
        UserResponseDTO currentUser = authenticateService.getCurrentUser();
        if(currentUser != null){
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                "Please log in again"
        );
    }

    //Logged out of current user
    @PostMapping("/auth/signout")
    public ResponseEntity<?> signOutUser(){
        ResponseCookie cookie = jwtService.getCleanCookie();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(
                        "Logged out successfully"
                );

    }

}
