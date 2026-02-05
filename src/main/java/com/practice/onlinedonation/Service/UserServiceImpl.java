package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Controller.AuthController;
import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.Repository.UserRepository;
import com.practice.onlinedonation.payload.UserDTO;
import com.practice.onlinedonation.payload.UserResponseDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthController auth;

    @Override
    public UserResponseDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User savedUser =  userRepository.save(user);
        return  modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    public Optional<User> findById(long userId) {

        return userRepository.findById(userId);
    }


}
