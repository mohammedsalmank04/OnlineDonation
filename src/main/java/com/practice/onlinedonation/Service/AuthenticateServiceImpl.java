package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Exception.ResourceNotFoundException;
import com.practice.onlinedonation.Model.AppRole;
import com.practice.onlinedonation.Model.Role;
import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.Repository.RoleRepository;
import com.practice.onlinedonation.Repository.UserRepository;
import com.practice.onlinedonation.payload.UserResponseDTO;
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

import java.util.*;

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
    @Autowired
    private RoleRepository roleRepository;

    static int count = 0;

    @Override
    public LoginResponse signUp(SignUpRequest user) {
        User u = modelMapper.map(user,User.class);
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        Set<String> roles = user.getRole();
        Set<Role> enumRoles = new HashSet<>();

        if(roles == null){
            Role role = roleRepository.findByRoleName(AppRole.ROLE_USER).orElseThrow(
                    () -> new RuntimeException("Error: Role not found" )
            );
            role.setRoleName(AppRole.ROLE_USER);
            enumRoles.add(role);
        }else{
            roles.forEach(
                    r -> {
                        switch (r){
                            case "admin":
                                Role role = roleRepository.findByRoleName(AppRole.ROLE_ADMIN).orElseThrow(
                                        () -> new RuntimeException("Error: Role not found" )
                                );

                                enumRoles.add(role);
                                break;
                            case "user":
                                Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_USER).orElseThrow(
                                        () -> new RuntimeException("Error: Role not found" )
                                );

                                enumRoles.add(adminRole);
                                break;



                        }
                    }
            );
        }
        u.setRole(enumRoles);
        return modelMapper.map(userRepository.save(u), LoginResponse.class);
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

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //A user without a cookie(Someone who was logged out logged in so attach cookies to it)

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if(userDetails == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    "Please login again"
            );
        }
        ResponseCookie jwtCookie = jwtService.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(
                i -> i.getAuthority()
        ).toList();
        UserResponseDTO response = modelMapper.map(userDetails,UserResponseDTO.class);
        response.setRoles(roles);
        return  ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,jwtCookie.toString()).body(
                response
        );
    }

    @Override
    public UserResponseDTO getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated()){
            Object principal = auth.getPrincipal();
            if(principal instanceof UserDetailsImpl){
                return modelMapper.map(
                        principal,UserResponseDTO.class
                );
            }
        }
        return null;

    }


    //This method uses jwt cookies to login not in use for now
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
}
