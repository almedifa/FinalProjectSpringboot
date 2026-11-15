package com.example.auth_service.exception;

public class UserAllReadyExistException extends RuntimeException {
    public UserAllReadyExistException(String username) {

        super("User with name " + username + " already exist");
    }
}
