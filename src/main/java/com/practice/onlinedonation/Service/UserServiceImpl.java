package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Controller.AuthController;
import com.practice.onlinedonation.Exception.ApiException;
import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.Repository.UserRepository;
import com.practice.onlinedonation.payload.UserCreationDTO;
import com.practice.onlinedonation.payload.UserDTO;
import com.practice.onlinedonation.payload.UserResponseDTO;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthController auth;

    @Override
    public UserResponseDTO createUser(UserCreationDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new ApiException("User " + user.getEmail() +" Already exists" );

            }

        System.out.println(user);
        return modelMapper.map(userRepository.save(user), UserResponseDTO.class);

    }

    @Override
    public Optional<User> findById(long userId) {

        return userRepository.findById(userId);
    }


}
