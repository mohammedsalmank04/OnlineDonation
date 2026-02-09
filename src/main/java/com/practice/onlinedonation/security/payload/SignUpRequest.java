package com.practice.onlinedonation.security.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {

    @Email
    @Column(unique = true, length = 100, nullable = false)
    private String email;
    @NotBlank
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @NotBlank
    private String password;
    private Set<String> role;
}
