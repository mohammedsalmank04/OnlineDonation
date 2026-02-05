package com.practice.onlinedonation.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String email;
    @ToString.Exclude
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;




}
