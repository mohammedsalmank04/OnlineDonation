package com.practice.onlinedonation.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDTO {

    private Long userId;
    private String username;
    private String email;

    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
