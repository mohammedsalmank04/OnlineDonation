package com.practice.onlinedonation.Controller;

import com.practice.onlinedonation.security.UserDetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    /*@Autowired
    Authentication authentication;





    public ResponseEntity<String>() userDashboard(){
        UserDetailsImpl currentUser =(UserDetailsImpl) authentication.getPrincipal();


    }*/
}
