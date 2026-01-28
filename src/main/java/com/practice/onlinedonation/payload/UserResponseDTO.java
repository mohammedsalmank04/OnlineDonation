package com.practice.onlinedonation.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private String username;
    private String email;

    private String firstName;
    private String lastName;
    private String phoneNumber;
}
