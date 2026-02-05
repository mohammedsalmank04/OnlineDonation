package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.payload.UserDTO;
import com.practice.onlinedonation.payload.UserResponseDTO;

import java.util.Optional;

public interface UserService {
    UserResponseDTO createUser(UserDTO userDTO);


    Optional<User> findById(long userId);
}
