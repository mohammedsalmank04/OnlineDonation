package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.payload.UserCreationDTO;
import com.practice.onlinedonation.payload.UserDTO;
import com.practice.onlinedonation.payload.UserResponseDTO;

import java.util.Optional;

public interface UserService {
    UserResponseDTO createUser(UserCreationDTO userDTO);


    Optional<User> findById(long userId);
}
