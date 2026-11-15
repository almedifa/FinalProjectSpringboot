package com.example.auth_service.exception;

import com.example.auth_service.dto.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAllReadyExistException.class)
    public ResponseEntity<BaseResponse<Object>> handleUserAlreadyExist(UserAllReadyExistException exception){
        return build(HttpStatus.BAD_REQUEST, exception.getMessage());
    }


    @ExceptionHandler(UnauthorizedException.class)
    public  ResponseEntity<BaseResponse<Object>> handleUnauthorized(UnauthorizedException exception){
        return build(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    private ResponseEntity<BaseResponse<Object>> build(HttpStatus status, String message){
        return ResponseEntity.status(status).body(BaseResponse.error(message));
    }
}
