package com.practice.onlinedonation.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BadCredentialResponse {

    private String message;
    private boolean status;
    private LocalDateTime timeStamp;
}
