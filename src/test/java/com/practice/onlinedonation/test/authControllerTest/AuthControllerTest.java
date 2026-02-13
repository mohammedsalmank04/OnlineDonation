package com.practice.onlinedonation.test.authControllerTest;

import com.practice.onlinedonation.Controller.AuthController;
import com.practice.onlinedonation.Model.User;
import com.practice.onlinedonation.Repository.UserRepository;
import com.practice.onlinedonation.Service.AuthenticateService;
import com.practice.onlinedonation.payload.UserResponseDTO;
import com.practice.onlinedonation.security.jwt.JwtService;
import com.practice.onlinedonation.security.payload.LoginResponse;
import com.practice.onlinedonation.security.payload.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticateService authenticateService;
    @Mock
   JavaMailSender javaMailSender;



    @Test
    void register_shouldReturnBadRequest_WhenEmailExists(){
        //Given
        SignUpRequest mockResponse = new SignUpRequest();
        mockResponse.setEmail("msk@gmail.com");

        when(userRepository.findByEmail("msk@gmail.com"))
                .thenReturn(Optional.of(new User()));

        //
        ResponseEntity<?> response = authController.register(mockResponse);

        //then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: username already exists",response.getBody());

        verify(authenticateService, never()).signUp(any());

    }

    @Test
    void register_shouldReturnCreated_whenUserIsCreated(){
        SignUpRequest mockSignUp = new SignUpRequest();
        mockSignUp.setEmail("msk@msk.com");

        LoginResponse mockLogin = new LoginResponse();


        when(userRepository.findByEmail("msk@msk.com")).thenReturn(Optional.empty());

        when(authenticateService.signUp(mockSignUp)).thenReturn(mockLogin);

        //when
        ResponseEntity<?> response = authController.register(mockSignUp);

        //Then
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(mockLogin, response.getBody());

        verify(authenticateService, times(1)).signUp(mockSignUp);

    }

    @Test
    void currentUser_ShouldReturnLoginAgain_WhenUserIsNotLoggedIn(){
        UserResponseDTO mockUser = new UserResponseDTO();
        mockUser.setEmail("msk@msk");
        mockUser.setUserId(1L);

        when(authenticateService.getCurrentUser()).thenReturn(null);

        ResponseEntity<?> response = authController.currentUser();
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
        assertEquals("Please log in again",response.getBody());
    }

    @Test
    void currentUser_ShouldReturnCurrentUser_WhenUserIsLoggedIn(){
        UserResponseDTO mockUser = new UserResponseDTO();
        mockUser.setEmail("msk@msk");
        mockUser.setUserId(1L);
        mockUser.setUsername("msk");

        when(authenticateService.getCurrentUser()).thenReturn(mockUser);

        ResponseEntity<?> response = authController.currentUser();

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(mockUser,response.getBody());


    }

}
