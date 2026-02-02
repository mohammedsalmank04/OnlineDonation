package com.practice.onlinedonation.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {


    private String username;
    private String email;

    private String firstName;
    private String lastName;
    private String phoneNumber;

}
