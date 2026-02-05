package com.practice.onlinedonation.Controller;

import com.practice.onlinedonation.Service.AuthenticateService;
import com.practice.onlinedonation.payload.UserDTO;
import com.practice.onlinedonation.security.UserDetails.UserDetailsImpl;
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
import org.springframework.security.core.Authentication;
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
    private ModelMapper mapper;

    @PostMapping("/auth/signUp")
    public ResponseEntity<LoginResponse> register(@RequestBody SignUpRequest user){
        LoginResponse loginResponse = authenticateService.signUp(user);
        return new ResponseEntity<>(loginResponse,HttpStatus.CREATED );

    }

    /*@PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse login = authenticateService.login(loginRequest);
        return new ResponseEntity<>(login, HttpStatus.OK);

    }*/

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return authenticateService.loginUsingCookie(loginRequest);


    }



    @GetMapping("/api/auth/me")
    public UserDTO currentUserName(Authentication authentication){
        if(authentication != null){
            UserDetailsImpl u = (UserDetailsImpl) authentication.getPrincipal();
            log.info("Getting the logged in user {}", u);
             return mapper.map(u, UserDTO.class);

        }

        return null;
    }

    @PostMapping("/auth/signout")
    public ResponseEntity<?> signOutUser(){
        ResponseCookie cookie = jwtService.getCleanCookie();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(
                        "You have Been Signed out"
                );

    }


}
