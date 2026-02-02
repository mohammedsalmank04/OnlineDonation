package com.practice.onlinedonation.Controller;

import com.practice.onlinedonation.Service.AuthenticateService;
import com.practice.onlinedonation.security.payload.LoginRequest;
import com.practice.onlinedonation.security.payload.LoginResponse;
import com.practice.onlinedonation.security.payload.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthenticateService authenticateService;

    @PostMapping("/auth/signUp")
    public ResponseEntity<LoginResponse> register(@RequestBody SignUpRequest user){
        LoginResponse loginResponse = authenticateService.signUp(user);
        return new ResponseEntity<>(loginResponse,HttpStatus.CREATED );

    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse login = authenticateService.login(loginRequest);
        return new ResponseEntity<>(login, HttpStatus.OK);

    }
}
