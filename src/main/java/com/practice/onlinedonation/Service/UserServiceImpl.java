package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.Repository.UserRepository;
import com.practice.onlinedonation.payload.UserDTO;
import com.practice.onlinedonation.payload.UserResponseDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserResponseDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User savedUser =  userRepository.save(user);
        return  modelMapper.map(savedUser, UserResponseDTO.class);
    }
}
