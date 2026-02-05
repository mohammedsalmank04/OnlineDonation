package com.practice.onlinedonation.Exception;

public class ApiException extends RuntimeException{
    private static final Long serialVersionUID = 1L;

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }
}
