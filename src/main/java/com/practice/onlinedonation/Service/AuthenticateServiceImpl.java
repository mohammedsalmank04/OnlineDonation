package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Exception.ResourceNotFoundException;
import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.Repository.UserRepository;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AuthenticateServiceImpl implements AuthenticateService{

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    static int count = 0;

    @Override
    public LoginResponse signUp(SignUpRequest user) {
        User u = modelMapper.map(user,User.class);
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return modelMapper.map(userRepository.save(u), LoginResponse.class);
    }

    @Override
    public LoginResponse login(LoginRequest user) {
        Authentication authentication;
       authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        User u = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("User", "User ID: " , user.getEmail())
        );
        log.info("Login method called: {}", count++);



        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseCookie jwtCookie = jwtService.generateJwtCookie(userDetails);

        //String jwtToken = jwtService.generateToken(userDetails);
        LoginResponse loginUser = modelMapper.map(userDetails,LoginResponse.class);
        //loginUser.setJwtToken(jwtToken);
        return loginUser;
    }

    @Override
    public ResponseEntity<?> loginUsingCookie(LoginRequest user) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("Message", "Bad Credentials");
            map.put("Status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }


        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseCookie jwtCookie = jwtService.generateJwtCookie(userDetails);

        //String jwtToken = jwtService.generateToken(userDetails);
        LoginResponse loginUser = modelMapper.map(userDetails,LoginResponse.class);
        //loginUser.setJwtToken(jwtToken);

        return  ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,jwtCookie.toString()).body(loginUser);
    }
}
