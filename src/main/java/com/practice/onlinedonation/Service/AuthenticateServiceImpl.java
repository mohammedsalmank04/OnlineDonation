package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Exception.ResourceNotFoundException;
import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.Repository.UserRepository;
import com.practice.onlinedonation.security.UserDetails.UserDetailsImpl;
import com.practice.onlinedonation.security.jwt.JwtService;
import com.practice.onlinedonation.security.payload.LoginRequest;
import com.practice.onlinedonation.security.payload.LoginResponse;
import com.practice.onlinedonation.security.payload.SignUpRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = jwtService.generateToken(userDetails);
        LoginResponse loginUser = modelMapper.map(userDetails,LoginResponse.class);
        loginUser.setJwtToken(jwtToken);
        return loginUser;
    }
}
