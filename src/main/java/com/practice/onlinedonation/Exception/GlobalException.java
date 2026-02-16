package com.practice.onlinedonation.Exception;

import com.practice.onlinedonation.payload.BadCredentialResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<BadCredentialResponse> loginException(LoginException e){
        BadCredentialResponse response = new BadCredentialResponse(
                e.getMessage(),
                false,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

    }


}
