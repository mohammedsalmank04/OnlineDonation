package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.payload.UserResponseDTO;
import com.practice.onlinedonation.security.payload.LoginRequest;
import com.practice.onlinedonation.security.payload.LoginResponse;
import com.practice.onlinedonation.security.payload.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticateService {

    LoginResponse signUp(SignUpRequest user);

    LoginResponse login(LoginRequest user);

    ResponseEntity<?> loginUsingCookie(LoginRequest user);



    UserResponseDTO getCurrentUser();
}
