package com.example.Product_Service.exception;

import com.example.Product_Service.dto.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> notFound(ProductNotFoundException exception) {
        log.warn("Product not found: {}", exception.getMessage());
        return response(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<BaseResponse<Void>> invalidProduct(InvalidProductException exception) {
        log.warn("Invalid product request: {}", exception.getMessage());
        return response(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> validation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .distinct().collect(Collectors.joining("; "));
        log.warn("Product validation failed: {}", message);
        return response(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<Void>> unreadable(HttpMessageNotReadableException exception) {
        log.warn("Unreadable product request body");
        return response(HttpStatus.BAD_REQUEST, "Body request tidak valid");
    }

    private ResponseEntity<BaseResponse<Void>> response(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(BaseResponse.failed(message));
    }
}
