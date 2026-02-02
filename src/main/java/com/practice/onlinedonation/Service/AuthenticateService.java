package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.security.payload.LoginRequest;
import com.practice.onlinedonation.security.payload.LoginResponse;
import com.practice.onlinedonation.security.payload.SignUpRequest;

public interface AuthenticateService {

    LoginResponse signUp(SignUpRequest user);

    LoginResponse login(LoginRequest user);

}
