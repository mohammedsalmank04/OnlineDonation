package com.practice.onlinedonation.Service;

import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.payload.UserDTO;
import com.practice.onlinedonation.payload.UserResponseDTO;

public interface UserService {
    UserResponseDTO createUser(UserDTO userDTO);
}
