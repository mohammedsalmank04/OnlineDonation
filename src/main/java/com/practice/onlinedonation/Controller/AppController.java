package com.practice.onlinedonation.Controller;

import com.practice.onlinedonation.Service.DonationCategoryService;
import com.practice.onlinedonation.Service.UserService;
import com.practice.onlinedonation.payload.UserDTO;
import com.practice.onlinedonation.payload.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    private UserService userService;
    @Autowired
    private DonationCategoryService donationCategoryService;

    @PostMapping("/user")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO userDTO){
    UserResponseDTO createdUser = userService.createUser(userDTO);
    return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
    }




}
